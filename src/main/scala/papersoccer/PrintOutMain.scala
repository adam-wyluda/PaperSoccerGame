package papersoccer

import generator.Generator
import papersoccer.util.Matrix
import util.Show.ops._

object PrintOutMain extends App {
  println(Matrix.create(5, 5, '.').show)
  println()
  println(Generator.kurnik.matrix.show)
}
