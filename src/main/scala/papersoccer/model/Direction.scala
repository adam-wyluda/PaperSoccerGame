package papersoccer.model

sealed trait Direction {
  lazy val reverse = Direction.reverse(this)

  def translate(from: (Int, Int)): (Int, Int) = Direction.translate(this)(from)
  def translate(x: Int, y: Int): (Int, Int)   = translate((x, y))
}

object Direction {
  case object NW extends Direction
  case object N  extends Direction
  case object NE extends Direction
  case object E  extends Direction
  case object SE extends Direction
  case object S  extends Direction
  case object SW extends Direction
  case object W  extends Direction

  val all = List(NW, N, NE, E, SE, S, SW, W)

  def between(from: (Int, Int), to: (Int, Int)): Option[Direction] =
    all.find(_.translate(from) == to)

  def reverse(direction: Direction) =
    direction match {
      case NW => SE
      case N  => S
      case NE => SW
      case E  => W
      case SE => NW
      case S  => N
      case SW => NE
      case W  => E
    }

  def translate(direction: Direction)(from: (Int, Int)): (Int, Int) = {
    val (x, y) = from

    direction match {
      case NW => (x - 1, y - 1)
      case N  => (x, y - 1)
      case NE => (x + 1, y - 1)
      case E  => (x + 1, y)
      case SE => (x + 1, y + 1)
      case S  => (x, y + 1)
      case SW => (x - 1, y + 1)
      case W  => (x - 1, y)
    }
  }
}
