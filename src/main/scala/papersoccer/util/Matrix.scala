package papersoccer.util

import Show.ops._

case class Matrix[A](
    width: Int,
    height: Int,
    array: IndexedSeq[A]
) {
  def get(x: Int, y: Int): Option[A] =
    if (fits(x, y)) Some(array(pos(x, y)))
    else None

  def get(pt: (Int, Int)): Option[A] = get(pt._1, pt._2)

  def set(x: Int, y: Int)(a: A): Matrix[A] =
    if (fits(x, y)) new Matrix(width, height, array.updated(pos(x, y), a))
    else this

  def set(pt: (Int, Int))(a: A): Matrix[A] = set(pt._1, pt._2)(a)

  def withPos: IndexedSeq[(A, Int, Int)] = array.zipWithIndex.map(overPos(Tuple3.apply).tupled)

  def mapWithPos[B](f: (A, Int, Int) => B): Matrix[B] =
    Matrix(
      width,
      height,
      withPos.map(f.tupled)
    )

  def overPos[B](f: (A, Int, Int) => B): (A, Int) => B =
    (a, i) => {
      val x = i % width
      val y = i / width
      f(a, x, y)
    }

  def rows: Iterator[IndexedSeq[A]] =
    if (width > 0) array.grouped(width)
    else Seq.empty.iterator

  def fillRect(x: Int, y: Int, width: Int, height: Int, fill: => A): Matrix[A] = {
    val (ix, iy) = inside(x, y)
    val (ex, ey) = inside(x + width, y + height)

    val allRows = rows.toIndexedSeq
    val first   = allRows.take(iy)
    val middle  = allRows.slice(iy, ey)
    val last    = allRows.drop(ey)

    val filled = Vector.fill(ex - ix)(fill)
    val newMiddle =
      middle.map { row =>
        row.take(ix) ++ filled ++ row.drop(ex)
      }

    val newArray = (first ++ newMiddle ++ last).flatten
    Matrix(this.width, this.height, newArray)
  }

  def fillHorizontal(x: Int, y: Int, width: Int, fill: => A): Matrix[A] =
    fillRect(x, y, width, 1, fill)

  def fillVertical(x: Int, y: Int, height: Int, fill: => A): Matrix[A] =
    fillRect(x, y, 1, height, fill)

  private def pos(x: Int, y: Int) = y * width + x

  private def fits(x: Int, y: Int)          = fitsBound(x, width) && fitsBound(y, height)
  private def fitsBound(i: Int, bound: Int) = i >= 0 && i < bound

  private def inside(x: Int, y: Int) = (insideBound(x, width), insideBound(y, height))
  private def insideBound(i: Int, bound: Int) =
    if (i < 0) 0
    else if (i > bound) bound
    else i
}

object Matrix {
  def create[A](width: Int, height: Int, default: => A): Matrix[A] =
    Matrix(width, height, Vector.fill(width * height)(default))

  def empty[A]: Matrix[A] = Matrix(0, 0, Vector.empty[A])

  implicit def showMatrix[A: Show]: Show[Matrix[A]] =
    _.rows
      .flatMap(row => row.map(_.show) :+ "\n")
      .mkString
}
