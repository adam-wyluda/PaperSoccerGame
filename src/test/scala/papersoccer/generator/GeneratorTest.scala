package papersoccer.generator

import papersoccer.util.Show.ops._
import utest._

object GeneratorTest extends TestSuite {
  val tests = this {
    'kurnik {
      import Generator.kurnik

      val expected =
        """####AAA####
          |##### #####
          |##       ##
          |##       ##
          |##       ##
          |##       ##
          |##       ##
          |##       ##
          |##       ##
          |##       ##
          |##       ##
          |##### #####
          |####BBB####
          |""".stripMargin
      val result = kurnik.matrix.show
      assert(result == expected)
    }

    'simple {
      val board = Generator.simple(5, 5)
      val expected =
        """##AAA##
          |### ###
          |##   ##
          |##   ##
          |##   ##
          |### ###
          |##BBB##
          |""".stripMargin
      val result = board.matrix.show
      assert(result == expected)
    }
  }
}
