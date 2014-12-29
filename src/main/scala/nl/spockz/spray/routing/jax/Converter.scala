package nl.spockz.spray.routing.jax

import spray.routing.Route

/**
 * Created by alessandro on 29/12/14.
 */
trait Converter {
  type ApplicationClasses = Seq[Class[_]]

  def packageToClasses(packageName: String) : ApplicationClasses

  def classesToRoute(classes: ApplicationClasses) : Route
}
