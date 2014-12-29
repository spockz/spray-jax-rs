package nl.spockz.spray.routing.jax.java;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

/**
 * Created by alessandro on 29/12/14.
 */
public class ControllerInvocationController {
    public ControllerInvocationController() {

    }

    @GET
    public Response doSomething() {
        System.out.println("doSomething is called!");
        return Response.ok("Hello World!").build();
    }
}
