package papersoccer.model

sealed trait MoveError

object MoveError {
  case object AlreadyPointed extends MoveError
  case object ThroughWall    extends MoveError
  case object NotConnected   extends MoveError
  case object OutsideBoard   extends MoveError
}
