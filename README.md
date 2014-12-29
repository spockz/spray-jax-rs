spray-jax-rs
============
[![Code Climate](https://codeclimate.com/github/spockz/spray-jax-rs/badges/gpa.svg)](https://codeclimate.com/github/spockz/spray-jax-rs)

# Overview

Spray JAX-RS is designed to create Spray routes from JAX-RS annotations placed on classes and methods used in a specific package.

The usage is as follows:

````scala
val myRoute = fromJaxAnnotation("some.package.name")
````


# Supported Annotations

# Unsupported Annotations
* ApplicationPath
* BeanParam
* ConstrainedTo
* Consumes
* CookieParam
* DefaultValue
* DELETE
* Encoded
* FormParam
* GET
* HEAD
* HeaderParam
* HttpMethod
* MatrixParam
* NameBinding
* OPTIONS
* Path
* PathParam
* POST
* Produces
* PUT
* QueryParam
