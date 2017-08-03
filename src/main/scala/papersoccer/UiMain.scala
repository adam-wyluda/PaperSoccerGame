package papersoccer

import javax.swing.SwingUtilities

import papersoccer.generator.Generator
import papersoccer.ui.GameWindow

object UiMain extends App {
  SwingUtilities.invokeLater(() => {
    val window = new GameWindow
    val board  = Generator.kurnik

    window.setVisible(true)
    window.setBoard(board)
  })
}
