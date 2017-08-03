package papersoccer.model

import Direction._
import Kind._
import papersoccer.util.Show

case class Point(
    kind: Kind,
    nw: Boolean,
    n: Boolean,
    ne: Boolean,
    e: Boolean,
    se: Boolean,
    s: Boolean,
    sw: Boolean,
    w: Boolean
) {
  def isBounceable = kind == Wall || isPointed
  def isPointed    = nw || n || ne || e || se || s || sw || w

  def pointTo(direction: Direction): Option[Point] = {
    val matcher: PartialFunction[Direction, Point] = {
      case NW if (!nw) => copy(nw = true)
      case N if (!n)   => copy(n = true)
      case NE if (!ne) => copy(ne = true)
      case E if (!e)   => copy(e = true)
      case SE if (!se) => copy(se = true)
      case S if (!s)   => copy(s = true)
      case SW if (!sw) => copy(sw = true)
      case W if (!w)   => copy(w = true)
    }

    matcher.lift.apply(direction)
  }
}

object Point {
  def create(kind: Kind) =
    Point(kind, false, false, false, false, false, false, false, false)

  val empty = create(Empty)
  val wall  = create(Wall)
  val goalA = create(GoalA)
  val goalB = create(GoalB)

  implicit def showPoint: Show[Point] = _.kind.char.toString
}
