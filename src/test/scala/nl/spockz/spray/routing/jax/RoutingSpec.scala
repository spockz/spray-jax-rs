package nl.spockz.spray.routing.jax

import org.specs2.matcher.ThrownMessages
import org.specs2.mutable.Specification
import spray.routing._
import spray.testkit.Specs2RouteTest
import spray.http.HttpResponse

abstract class RoutingSpec extends Specification with Directives with Specs2RouteTest with ThrownMessages {

  val Ok = HttpResponse()
  val completeOk = complete(Ok)

  def echoComplete[T]: T ⇒ Route = { x ⇒ complete(x.toString) }
  def echoComplete2[T, U]: (T, U) ⇒ Route = { (x, y) ⇒ complete(s"$x $y") }
}