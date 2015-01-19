spray-jax-rs
============
![Code Ship](https://codeship.com/projects/98035900-81fb-0132-b737-2e1fa562aa85/status?branch=master)

# Overview

Spray JAX-RS is designed to create Spray routes from JAX-RS annotations placed on classes and methods used in a specific package.
At this point supporting filters and interceptors, as well as performing DI is not in scope. This functionality is reserved for
a later time or different packages.

The usage is as follows:

````scala
val myRoute = fromJaxAnnotation("some.package.name")
````


# Supported Annotations

* DELETE
* GET
* HEAD
* HttpMethod
* OPTIONS
* POST
* PUT


# Unsupported Annotations
* ApplicationPath
* BeanParam
* ConstrainedTo
* Consumes
* CookieParam
* DefaultValue
* Encoded
* FormParam
* HeaderParam
* MatrixParam
* NameBinding
* Path
* PathParam
* Produces
* QueryParam
