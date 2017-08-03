package papersoccer.util

import Show.ops._
import utest._

object MatrixTest extends TestSuite {
  val tests = this {
    val empty  = Matrix.empty[Char]
    val matrix = Matrix.create(5, 5, '.')

    'create {
      'empty {
        assert(Matrix.empty[Char].show == "")
      }

      'matrix {
        val expected =
          """.....
            |.....
            |.....
            |.....
            |.....
            |""".stripMargin
        val result = matrix.show
        assert(result == expected)
      }
    }

    'get {
      'empty {
        assert(empty.get(0, 0) == None)
      }

      'existing {
        assert(matrix.get(1, 1) == Some('.'))
      }

      'outsideX {
        assert(matrix.get(5, 1) == None)
      }

      'outsideY {
        assert(matrix.get(1, 5) == None)
      }
    }

    'set {
      'empty {
        assert(empty.set(0, 0)('.') == empty)
      }

      val matrix = Matrix.create(5, 5, '.')

      'inside {
        val updated = matrix.set(1, 1)('x')
        val expected =
          """.....
            |.x...
            |.....
            |.....
            |.....
            |""".stripMargin
        assert(updated.show == expected)
      }

      'outsideX {
        assert(matrix.set(5, 1)('x') == matrix)
      }

      'outsideY {
        assert(matrix.set(1, 5)('x') == matrix)
      }
    }

    'fillRect {
      'empty {
        assert(empty.fillRect(0, 0, 10, 10, 'x') == empty)
      }

      'inside {
        val filled = matrix.fillRect(1, 1, 3, 2, 'x')
        val expected =
          """.....
            |.xxx.
            |.xxx.
            |.....
            |.....
            |""".stripMargin
        assert(filled.show == expected)
      }

      'outsideX {
        val filled = matrix.fillRect(-1, 1, 7, 2, 'x')
        val expected =
          """.....
            |xxxxx
            |xxxxx
            |.....
            |.....
            |""".stripMargin
        assert(filled.show == expected)
      }

      'outsideY {
        val filled = matrix.fillRect(1, -1, 3, 7, 'x')
        val expected =
          """.xxx.
            |.xxx.
            |.xxx.
            |.xxx.
            |.xxx.
            |""".stripMargin
        assert(filled.show == expected)
      }

      'uneven {
        val uneven = Matrix
          .create(6, 5, '.')
          .fillRect(1, 1, 3, 3, 'x')
        val expected =
          """......
            |.xxx..
            |.xxx..
            |.xxx..
            |......
            |""".stripMargin
      }
    }

    'fillHorizontal {
      'empty {
        assert(empty.fillHorizontal(0, 0, 10, 'x') == empty)
      }

      'inside {
        val filled = matrix.fillHorizontal(1, 1, 3, 'x')
        val expected =
          """.....
            |.xxx.
            |.....
            |.....
            |.....
            |""".stripMargin
        assert(filled.show == expected)
      }

      'outsideX {
        val filled = matrix.fillHorizontal(-1, 1, 7, 'x')
        val expected =
          """.....
            |xxxxx
            |.....
            |.....
            |.....
            |""".stripMargin
        assert(filled.show == expected)
      }

      'outsideY {
        val filled = matrix.fillHorizontal(1, -1, 3, 'x')
        val expected =
          """.....
            |.....
            |.....
            |.....
            |.....
            |""".stripMargin
        assert(filled.show == expected)
      }
    }

    'fillVertical {
      'empty {
        assert(empty.fillVertical(0, 0, 10, 'x') == empty)
      }

      'inside {
        val filled = matrix.fillVertical(1, 1, 3, 'x')
        val expected =
          """.....
            |.x...
            |.x...
            |.x...
            |.....
            |""".stripMargin
        assert(filled.show == expected)
      }

      'outsideX {
        val filled = matrix.fillVertical(-1, 1, 3, 'x')
        val expected =
          """.....
            |.....
            |.....
            |.....
            |.....
            |""".stripMargin
        assert(filled.show == expected)
      }

      'outsideY {
        val filled = matrix.fillVertical(1, -1, 7, 'x')
        val expected =
          """.x...
            |.x...
            |.x...
            |.x...
            |.x...
            |""".stripMargin
        assert(filled.show == expected)
      }
    }
  }
}
