package papersoccer.model

sealed trait Kind {
  val char = Kind.char(this)

  val isBlocking = this.isInstanceOf[Kind.Blocking]
  val isGameEnd  = this.isInstanceOf[Kind.EndingGame]
}

object Kind {
  sealed trait EndingGame extends Kind
  sealed trait Blocking   extends Kind

  case object Empty extends Kind
  case object Wall  extends Kind with Blocking
  case object GoalA extends Kind with Blocking with EndingGame
  case object GoalB extends Kind with Blocking with EndingGame

  val char: Kind => Char = {
    case Empty => ' '
    case Wall  => '#'
    case GoalA => 'A'
    case GoalB => 'B'
  }
}
