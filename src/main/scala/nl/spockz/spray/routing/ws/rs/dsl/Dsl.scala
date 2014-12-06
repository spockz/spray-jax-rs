package nl.spockz.spray.routing.ws.rs.dsl

/**
 * Created by alessandro on 06/12/14.
 */
sealed trait Route {

}

sealed trait Method extends Route {}

case class Endpoint() extends Route

case class Path(path: String) extends Route

case object GET extends Method
case object POST extends Method
case object PUT  extends Method
case object DELETE extends Method
case object HEAD extends Method

class Dsl {

}
