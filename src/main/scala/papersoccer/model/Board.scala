package papersoccer.model

import papersoccer.util.Matrix

case class Board(
    matrix: Matrix[Point],
    from: (Int, Int)
) {
  def point(direction: Direction): Either[MoveError, Board] =
    point(direction.translate(from))

  def point(x: Int, y: Int): Either[MoveError, Board] = point((x, y))

  def point(to: (Int, Int)): Either[MoveError, Board] =
    for {
      direction <- Direction
        .between(from, to)
        .toRight(MoveError.NotConnected)

      s0 <- matrix
        .get(from)
        .toRight(MoveError.OutsideBoard)
      s1 <- matrix
        .get(to)
        .toRight(MoveError.OutsideBoard)

      _ <- canPass(s0, s1)

      p0 <- s0
        .pointTo(direction)
        .toRight(MoveError.AlreadyPointed)
      p1 <- s1
        .pointTo(direction.reverse)
        .toRight(MoveError.AlreadyPointed)

      newMatrix = matrix
        .set(from)(p0)
        .set(to)(p1)
    } yield Board(newMatrix, to)

  private def canPass(s0: Point, s1: Point): Either[MoveError, Unit] =
    if (s0.kind.isBlocking && s1.kind.isBlocking) Left(MoveError.ThroughWall)
    else Right(())

  def dimension = (matrix.width, matrix.height)

  def isGameEnd = matrix.get(from).forall(_.kind.isGameEnd)

  def wallPoints: Matrix[Boolean] = {
    val deepWalls =
      matrix.mapWithPos { (p, x, y) =>
        val blocking = p.kind.isBlocking
        val otherToo =
          Direction.all.forall { dir =>
            matrix
              .get(dir.translate(x, y))
              .forall(_.kind.isBlocking)
          }
        blocking && otherToo
      }
    deepWalls.mapWithPos { (deepWall, x, y) =>
      val notWall = !deepWall
      val hasWall =
        Direction.all.exists { dir =>
          deepWalls
            .get(dir.translate(x, y))
            .getOrElse(true)
        }
      notWall && hasWall
    }
  }
}
