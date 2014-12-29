package nl.spockz.spray.routing.jax

import spray.http.HttpHeaders.RawHeader
import spray.http.{HttpHeader, HttpEntity, HttpResponse}
import spray.httpx.marshalling.ToResponseMarshallable

import scala.reflect.ClassTag
import scala.reflect.runtime._
import scala.reflect.runtime.universe._

import spray.routing._
import spray.routing.Directives._

import javax.ws.rs.core.{MultivaluedMap, Response}
import collection.JavaConverters._

/**
 * Created by alessandro on 29/12/14.
 */
trait SimpleConverter extends Converter {

  import SimpleConverter._

  def packageToClasses[T : TypeTag](packageName: String): ApplicationClasses = ???

  def classesToRoute[T : TypeTag](classes: ApplicationClasses): Route = {
    println(implicitly[TypeTag[T]])
    routeTreeToRoute(classesToRouteTree(classes))
  }


  //    classesToRouteTree andThen routeTreeToRoute

  /*** To internal representation ***/

  def classesToRouteTree[T : TypeTag](classes: ApplicationClasses): RouteTree =
  {
    println(implicitly[TypeTag[T]])
    Node(List.empty, classes.map(clazz => classToRouteTree(clazz)).toList)
  }

  def classToRouteTree[T: TypeTag: ClassTag](clazz: Class[T]): RouteTree = {
    val tpe : Type = typeOf[T]
    println("classToRouteTree", implicitly[TypeTag[T]])
    tpe.members.foreach { m =>
      println(m.name)
    }
    println("classToRouteTree-end", implicitly[TypeTag[T]])
    Node(Seq.empty, tpe.members.filter(m => m.isMethod && m.isPublic && m.annotations.nonEmpty).map { decl =>
      Node(decl.annotations, List(methodToRouteTree(tpe, decl.asMethod)))
    }.asInstanceOf[List[RouteTree]])
  }

  def methodToRouteTree[T: TypeTag : ClassTag](clazz: Type, method: MethodSymbol): RouteTree = Leaf {
    complete {
      // ToDo: Create param  matchers here?
      val params = List.empty
      val controller : T = createInstance[T](params)(0)
      println(method.name)
      val response = currentMirror.reflect(controller).reflectMethod(method).apply()
      response match {
        case res : Response => wsrsToSpray(res)
        case _                            => "err"
      }
    }
  }

  /*** To route ***/

  def routeTreeToRoute(routeTree: RouteTree): Route = routeTree match {
    case Node(annotations, subTree) =>
      subTree.map(routeTreeToRoute).reduce((left, right) => left ~ right)
    case Leaf(route) => route
  }

  private def createInstance[T : TypeTag](args: AnyRef*)(ctor: Int = 0): T = {
    val tt = typeTag[T]
    println(tt)
    currentMirror.reflectClass(tt.tpe.typeSymbol.asClass).reflectConstructor({
      println(tt.tpe.members.count(m => m.isMethod && m.asMethod.isConstructor))
      tt.tpe.members.filter(m =>
        m.isMethod && m.asMethod.isConstructor
      ).iterator.toSeq(ctor).asMethod
    })(args: _*).asInstanceOf[T]
  }
}

object SimpleConverter {

  sealed trait RouteTree

  case class Node(annotation: Seq[Annotation], subTree: List[RouteTree]) extends RouteTree

  case class Leaf(controller: Route) extends RouteTree


  def wsrsToSpray(wsResponse: Response) : HttpResponse =
    HttpResponse(status = wsResponse.getStatus, entity = HttpEntity(wsResponse.getEntity.toString), getHeaderList(wsResponse.getStringHeaders))

  def getHeaderList(headers: MultivaluedMap[String, String]) : List[HttpHeader] =
    for {
      entry <- headers.entrySet.asScala.toList
      key = entry.getKey
      value <- entry.getValue.asScala
    } yield RawHeader(key, value)
}