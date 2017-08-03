package papersoccer.util

import simulacrum._

@typeclass
trait Show[A] {
  def show(a: A): String
}

object Show {
  implicit def showString: Show[String]   = _.toString
  implicit def showChar: Show[Char]       = _.toString
  implicit def showBoolean: Show[Boolean] = b => if (b) "X" else "."
}
