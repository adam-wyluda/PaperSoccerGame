package papersoccer.ui

import java.awt.Color

object UiConsts {
  val offsetX = 30
  val offsetY = 50
  val space   = 40

  val dotSize    = 1
  val cursorSize = 7

  val boardColor = new Color(0, 120, 60)
  val lineColor  = Color.WHITE

  def calcX(x: Int) = x * space + offsetX
  def calcY(y: Int) = y * space + offsetY

  def calcPixels(width: Int, height: Int): (Int, Int) =
    (calcX(width - 1) + offsetX, calcY(height - 1) + offsetY)
}
