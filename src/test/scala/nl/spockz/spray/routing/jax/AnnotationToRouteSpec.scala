package nl.spockz.spray.routing.jax

import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application
/**
 * This test class tests whether the correct routes are being generated from the
 * different annotations. The methods are simple endpoints. Argument resolving is
 * tested elsewhere.
 */
class AnnotationToRouteSpec extends RoutingSpec {
  trait SimpleTestApp {

    @ApplicationPath("app")
    class SomeApp extends Application

    class GetController {

    }
  }

  "the annotation to route resolver" should {
//    "correctly resolve methods" in {
//      "add GET methods" in {
//
//      }
//
//      "add POST methods" in {
//
//      }
//
//      "add PUT methods" in {}
//
//      "add DELETE methods" in {}
//
//      "add OPTIONS methods" in {}
//
//      "add HEAD methods" in {}
//    }
//
//    "correctly handle content-types" in {
//      "translate Consumes" in {
//      }
//
//      "translate Produces" in {
//
//      }
//    }
//
//    "correctly handle arguments" in {
//
//    }
  }
}
