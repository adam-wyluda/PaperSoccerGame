package papersoccer.generator

import papersoccer.model._
import papersoccer.util.Matrix

object Generator {
  val kurnik = simple(9, 11)

  def simple(width: Int, height: Int): Board = {
    val boardWidth  = width + 2
    val boardHeight = height + 2

    val fieldX = 1
    val fieldY = 1

    val innerX = fieldX + 1
    val innerY = fieldY + 1

    val innerWidth  = width - 2
    val innerHeight = height - 2

    val centerX = innerX + (innerWidth - 1) / 2
    val centerY = innerY + (innerHeight - 1) / 2

    val goalStart = centerX - 1
    val goalWidth = 3

    val matrix =
      Matrix
        .create[Point](boardWidth, boardHeight, Point.wall)
        .fillRect(
          x = innerX,
          y = innerY,
          width = innerWidth,
          height = innerHeight,
          fill = Point.empty
        )
        .set(centerX, fieldY)(Point.empty)
        .set(centerX, fieldY + innerHeight + 1)(Point.empty)
        .fillHorizontal(
          x = goalStart,
          y = fieldY - 1,
          width = goalWidth,
          fill = Point.goalA
        )
        .fillHorizontal(
          x = goalStart,
          y = fieldY + innerHeight + 2,
          width = goalWidth,
          fill = Point.goalB
        )

    Board(matrix, (centerX, centerY))
  }
}
