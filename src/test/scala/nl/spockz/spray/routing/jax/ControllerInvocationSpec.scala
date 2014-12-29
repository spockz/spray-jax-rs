package nl.spockz.spray.routing.jax

import nl.spockz.spray.routing.jax.java.ControllerInvocationController

/**
 * Test class for testing whether the controller methods are called with
 * the correct arguments in the correct order.
 */
class ControllerInvocationSpec extends RoutingSpec {

  "the resolver" should {
    "successfully call a single method" in {
      val converter = new SimpleConverter {

      }
      val route = converter.classesToRoute(Seq(classOf[ControllerInvocationController]))
      Get() ~> route ~> check {
        responseAs[String] must contain("Say hello")
      }
    }
  }
}
