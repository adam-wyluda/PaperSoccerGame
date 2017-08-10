package papersoccer.ui

import java.awt.Graphics
import java.awt.event.{MouseAdapter, MouseEvent, MouseMotionAdapter}
import javax.swing.JFrame

import papersoccer.model.{Board, Direction, MoveError}

class GameWindow extends JFrame {
  private var boardOpt: Option[Board]       = None
  private var cursorOpt: Option[(Int, Int)] = None

  setTitle("PaperSoccer")
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  addMouseListener(new MouseAdapter {
    override def mouseClicked(e: MouseEvent): Unit = {
      onClicked
    }

    override def mouseEntered(e: MouseEvent): Unit = {
      onMoved(e.getX(), e.getY())
    }

    override def mouseExited(e: MouseEvent): Unit = {
      cursorOpt = None
    }
  })

  addMouseMotionListener(new MouseMotionAdapter {
    override def mouseMoved(e: MouseEvent): Unit = {
      onMoved(e.getX(), e.getY())
    }
  })

  def setBoard(board: Board): Unit = {
    boardOpt match {
      case Some(prevBoard) =>
        if (prevBoard.dimension != board.dimension) {
          val (width, height) = board.dimension
          updateSize(width, height)
        }
      case None =>
        val (width, height) = board.dimension
        updateSize(width, height)
    }

    boardOpt = Some(board)

    repaint()
  }

  private def onMoved(x: Int, y: Int): Unit = {
    cursorOpt = Some((x, y))
    repaint()
  }

  private def onClicked: Unit = {
    for ((board, (x, y)) <- boardOpt zip closestPoint) {
      board
        .point(x, y)
        .fold(
          showError,
          setBoard
        )
    }
  }

  private def updateSize(width: Int, height: Int): Unit = {
    val (pixelWidth, pixelHeight) = UiConsts.calcPixels(width, height)
    setSize(pixelWidth, pixelHeight)
  }

  override def paint(g: Graphics): Unit = {
    super.paint(g)

    boardOpt.foreach(paintGame(g))
  }

  private def pointable: Option[(Int, Int)] =
    for {
      board  <- boardOpt
      (x, y) <- closestPoint
      _      <- board.point(x, y).right.toOption
    } yield (x, y)

  private def closestPoint: Option[(Int, Int)] =
    cursorOpt.map {
      case (px, py) =>
        val x = (px + UiConsts.space / 2 - UiConsts.offsetX) / UiConsts.space
        val y = (py + UiConsts.space / 2 - UiConsts.offsetY) / UiConsts.space
        (x, y)
    }

  private def paintGame(g: Graphics)(board: Board): Unit = {
    import UiConsts.{calcPixels, calcX, calcY}

    val (width, height)     = board.dimension
    val (pxWidth, pxHeight) = calcPixels(width, height)
    val (curX, curY)        = board.from

    g.setColor(UiConsts.boardColor)
    g.fillRect(0, 0, pxWidth, pxHeight)

    g.setColor(UiConsts.lineColor)
    for {
      x <- 0 until width
      y <- 0 until height
    } {
      g.drawOval(
        calcX(x) - UiConsts.dotSize / 2,
        calcY(y) - UiConsts.dotSize / 2,
        UiConsts.dotSize,
        UiConsts.dotSize
      )
    }

    g.fillOval(
      calcX(curX) - UiConsts.cursorSize / 2,
      calcY(curY) - UiConsts.cursorSize / 2,
      UiConsts.cursorSize,
      UiConsts.cursorSize
    )

    val lowerStraight = List(Direction.S, Direction.E)
    val wallPoints    = board.wallPoints

    wallPoints.foreachWithPos { (w, x, y) =>
      for {
        dir <- lowerStraight
        (tx, ty) = dir.translate(x, y)
        if (w && wallPoints.get(tx, ty).contains(w))
      } g.drawLine(
        calcX(x),
        calcY(y),
        calcX(tx),
        calcY(ty)
      )
    }

    val lower = List(Direction.SW, Direction.S, Direction.SE, Direction.E)
    board.matrix.foreachWithPos { (p, x, y) =>
      for {
        dir <- lower
        if p.pointsTo(dir)
        (tx, ty) = dir.translate(x, y)
      } g.drawLine(
        calcX(x),
        calcY(y),
        calcX(tx),
        calcY(ty)
      )
    }

    for ((x, y) <- pointable) {
      g.drawOval(
        calcX(x) - UiConsts.cursorSize / 2,
        calcY(y) - UiConsts.cursorSize / 2,
        UiConsts.cursorSize,
        UiConsts.cursorSize
      )
    }
  }

  private def showError(error: MoveError): Unit = {
    // TODO Show error
  }
}
