package nl.spockz.spray.routing.jax

import spray.routing.Route
import scala.reflect.runtime.universe._

trait Converter {
  type ApplicationClasses = Seq[Class[_]]

  def packageToClasses[T : TypeTag](packageName: String) : ApplicationClasses

  def classesToRoute[T : TypeTag](classes: ApplicationClasses) : Route
}
