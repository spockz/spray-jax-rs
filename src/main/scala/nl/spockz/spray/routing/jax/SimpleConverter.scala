package nl.spockz.spray.routing.jax

import javax.ws.rs._
import javax.ws.rs.core.{MultivaluedMap, Response}

import nl.spockz.reflection.Util._
import spray.http.HttpHeaders.RawHeader
import spray.http.{HttpEntity, HttpHeader, HttpResponse}
import spray.routing.Directives._
import spray.routing._

import scala.collection.JavaConverters._
import scala.reflect.ClassTag
import scala.reflect.runtime._
import scala.reflect.runtime.universe._

/**
 * Created by alessandro on 29/12/14.
 */
trait SimpleConverter extends Converter {

  import nl.spockz.spray.routing.jax.SimpleConverter._

  def packageToClasses[T: TypeTag](packageName: String): ApplicationClasses = ???

  def classesToRoute[T: TypeTag](classes: ApplicationClasses): Route = {
    println(implicitly[TypeTag[T]])
    routeTreeToRoute(classesToRouteTree(classes))
  }

  //    classesToRouteTree andThen routeTreeToRoute

  /** * To internal representation ***/

  def classesToRouteTree[T: TypeTag](classes: ApplicationClasses): RouteTree = {
    println(implicitly[TypeTag[T]])
    Node(List.empty, classes.map(clazz => classToRouteTree(clazz)).toList)
  }

  /**
   * Convert a single class to a route tree looking at the annotations of the
   * class and the action methods and their annotation in the class. A method
   * is an action method if it returns an [[javax.ws.rs.core.Response]].
   * @return An RouteTree representing this controller
   */
  def classToRouteTree[T: TypeTag : ClassTag](clazz: Class[T]): RouteTree = {
    val tpe: Type = typeOf[T]
    Node(Seq.empty, tpe.members.filter(isControllerMethod).map { decl =>
      Node(decl.annotations, List(methodToRouteTree(tpe, decl.asMethod)))
    }.asInstanceOf[List[RouteTree]])
  }

  /**
   *
   * @param clazz
   * @param method
   * @tparam T
   * @return
   */
  def methodToRouteTree[T: TypeTag : ClassTag](clazz: Type, method: MethodSymbol): RouteTree = Leaf(method.annotations, {

    complete {
      // ToDo: Create param  matchers here?
      val params = List.empty
      val controller: T = createInstance[T](params)(0)
      println(method.name)
      val response = currentMirror.reflect(controller).reflectMethod(method).apply()
      response match {
        case res: Response => wsrsToSpray(res)
        case _             => "err"
      }
    }
  })

  /** * To route ***/

  def routeTreeToRoute(routeTree: RouteTree): Route = routeTree match {
    case Node(_, Nil)               =>
      // Requests that end up here cannot be handled at all.
      // TBD: Should we make this an error, or warning?
      _.reject()
    case Node(annotations, subTree) =>
      subTree.map(routeTreeToRoute).reduce((left, right) => left ~ right)
    case Leaf(annotations, route)   => annotationsToRoute(annotations)(route)
  }

  def annotationToRoute(annotation: Annotation)(innerRoute: => Route): Route =
    annotationMap get annotation.tree.tpe match {
      case Some(directive) => directive { innerRoute }
      case None            => innerRoute
    }

  val annotationMap: Map[Type, Directive0] =
    List(typeOf[GET] -> get, typeOf[POST] -> post, typeOf[HEAD] -> head,
      typeOf[DELETE] -> delete, typeOf[OPTIONS] -> options, typeOf[PUT] -> put).toMap

  def annotationsToRoute(annotations: Seq[Annotation])(innerRoute: => Route): Route = {
    annotations match {
      case annotation +: xs => {
        annotationToRoute(annotation)(annotationsToRoute(xs)(innerRoute))
      }
      case Seq()            => innerRoute
    }
  }


  /**
   * A method is a controller method when it returns a jax-rs Response and has a method annotation.
   */
  private def isControllerMethod(symbol: Symbol): Boolean =
    symbol.isMethod && symbol.asMethod.returnType <:< typeOf[Response] && symbol.asMethod.annotations.exists(methodAnnotations contains _.tree.tpe)

  private val methodAnnotations = List(typeOf[HttpMethod], typeOf[GET], typeOf[POST], typeOf[HEAD], typeOf[DELETE], typeOf[OPTIONS], typeOf[PUT])
}

object SimpleConverter {

  sealed trait RouteTree

  case class Node(annotation: Seq[Annotation], subTree: List[RouteTree]) extends RouteTree

  case class Leaf(annotations: Seq[Annotation], controller: Route) extends RouteTree


  def wsrsToSpray(wsResponse: Response): HttpResponse = {
    val entity = wsResponse.getEntity match {
      case null => HttpEntity.Empty
      case e    => HttpEntity(e.toString)
    }
    HttpResponse(status = wsResponse.getStatus, entity = entity, getHeaderList(wsResponse.getStringHeaders))
  }

  def getHeaderList(headers: MultivaluedMap[String, String]): List[HttpHeader] =
    for {
      entry <- headers.entrySet.asScala.toList
      key = entry.getKey
      value <- entry.getValue.asScala
    } yield RawHeader(key, value)
}