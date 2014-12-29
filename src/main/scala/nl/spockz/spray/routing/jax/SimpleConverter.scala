package nl.spockz.spray.routing.jax

import spray.routing._

/**
 * Created by alessandro on 29/12/14.
 */
trait SimpleConverter extends Converter {
  def packageToClasses(packageName: String) : ApplicationClasses = ???

  def classesToRoute(classes: ApplicationClasses) : Route = ???
}
