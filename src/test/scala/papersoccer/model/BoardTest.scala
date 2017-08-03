package papersoccer.model

import utest._

import papersoccer.generator.Generator
import papersoccer.util.Show.ops._

object BoardTest extends TestSuite {
  val tests = this {
    val board = Generator.kurnik

    'point {
      'direction {
        'right {
          val Right(result) = board.point(Direction.SE)
          assert(result.from == Direction.SE.translate(board.from))

          val (px, py) = board.from
          val (nx, ny) = result.from

          val prev = result.matrix.get(px, py).get
          val next = result.matrix.get(nx, ny).get

          assert(prev == Point.empty.pointTo(Direction.SE).get)
          assert(next == Point.empty.pointTo(Direction.NW).get)
        }

        'alreadyPointed {
          val Right(next)                    = board.point(Direction.N)
          val Left(MoveError.AlreadyPointed) = next.point(Direction.S)
        }

        'outsideBoard {
          val newBoard                     = board.copy(from = (0, 0))
          val Left(MoveError.OutsideBoard) = newBoard.point(Direction.N)
        }

        'throughWall {
          def loop(board: Board, n: Int): Board =
            if (n > 0) loop(board.point(Direction.E).right.get, n - 1)
            else board

          val beforeWall = loop(board, 4)

          val Left(MoveError.ThroughWall) = beforeWall.point(Direction.E)
        }
      }

      'toNotConnected {
        val Left(MoveError.NotConnected) =
          board.point(Direction.N.translate(Direction.NW.translate(board.from)))
      }
    }

    'isGameEnd {
      'not {
        assert(!board.isGameEnd)
      }

      'shootGoal {
        def loop(board: Board, n: Int): Board =
          if (n > 0) loop(board.point(Direction.N).right.get, n - 1)
          else board

        val beforeGoal = loop(board, 5)
        val atGoal     = loop(board, 6)

        assert(!beforeGoal.isGameEnd)
        assert(atGoal.isGameEnd)
      }
    }

    'wallPoints {
      val walls = board.wallPoints
      val expected =
        """....XXX....
          |.XXXX.XXXX.
          |.X.......X.
          |.X.......X.
          |.X.......X.
          |.X.......X.
          |.X.......X.
          |.X.......X.
          |.X.......X.
          |.X.......X.
          |.X.......X.
          |.XXXX.XXXX.
          |....XXX....
          |""".stripMargin
      val result = walls.show

      assert(expected == result)
    }
  }
}
