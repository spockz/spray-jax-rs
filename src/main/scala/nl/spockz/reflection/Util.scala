package nl.spockz.reflection

import scala.reflect.runtime._
import scala.reflect.runtime.universe._

object Util {
  def createInstance[T: TypeTag](args: AnyRef*)(ctor: Int = 0): T = {
    val tt = typeTag[T]
    println(tt)
    currentMirror.reflectClass(tt.tpe.typeSymbol.asClass).reflectConstructor({
      tt.tpe.members.filter(m =>
        m.isMethod && m.asMethod.isConstructor
      ).iterator.toSeq(ctor).asMethod
    })(args: _*).asInstanceOf[T]
  }
}
