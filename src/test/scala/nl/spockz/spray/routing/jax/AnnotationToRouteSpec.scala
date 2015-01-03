package nl.spockz.spray.routing.jax

import javax.ws.rs.{GET, POST, ApplicationPath}
import javax.ws.rs.core.{Application, Response}

/**
 * This test class tests whether the correct routes are being generated from the
 * different annotations. The methods are simple endpoints. Argument resolving is
 * tested elsewhere.
 */
class AnnotationToRouteSpec extends RoutingSpec {
  object SimpleTestApp {

    @ApplicationPath("app")
    class SomeApp extends Application



    class GetController {

    }
  }

  val converter = new SimpleConverter {  }

  "the annotation to route resolver" should {
    "not add methods without http method annotation" in {
      val route = converter.routeTreeToRoute(converter.classToRouteTree(classOf[NoMethodsController]))
      Get() ~> route ~> check {
        handled must beFalse
      }
    }

    "should reject methods with multiple http method annotations" in {
      true must beTrue
    }

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

class NoMethodsController {
  def noMethod() : Response = {
    Response.ok().build()
  }
}

class MultipleMethodsController {
  @GET
  @POST
  def multipleMethods : Response = {
    Response.ok().build()
  }
}