import kotlin.system.exitProcess

/**
 * TicTacToe
 * Author: Alexandre Madeira
 * Description: Console-based TicTacToe where you can play with different-sized boards based on the value of N.
 * The value of N must be between 3 and 30. (You're a real mad lad if you play 30)
 */

object TicTacToe {

    private var n = 3 //Has to be between 3 and 30.
    private var gameSize = n * n
    private const val PLAYED_X = "  X  "
    private const val PLAYED_O = "  O  "

    /**
     * Obtains the value of the given spot.
     * @param game  The current game.
     * @param key   The number of the spot.
     * @return The value of the given spot.
     */
    private fun getValue(game: HashMap<Int, String>, key: Int) = if (!game.containsKey(key)) getSpot(key) else game[key]

    /**
     * Obtains a string that will represent the spot on the board.
     * @param n The number that will be written.
     * @return The string that represents the spot.
     */
    private fun getSpot(n: Int): String {
        return when {
            n > 99 -> " $n "
            n > 9 -> " $n  "
            else -> "  $n  "
        }
    }

    /**
     * Obtains a line division for each row with the right size.
     * @return The line division.
     */
    private fun lineDivision(): String {
        var str = "-----"
        repeat(6 * n - 6) {
            str += "-"
        }
        return str
    }

    /**
     * Prints a division with a reasonable size between different instances of the same game.
     */
    private fun gameDivision() {
        var str = "====="
        repeat(6 * n - 6) {
            str += "="
        }
        str += str
        println(str)
    }

    /**
     * Prints the given game.
     * @param game  The game that is going to be printed.
     */
    private fun printGame(game: HashMap<Int, String>) {
        var i = 1
        while (i <= gameSize) {
            when {
                (i - 1) % n == 0 -> {
                    print("${getValue(game, i)}|")
                    i++
                }
                i < gameSize && i % n == 0 -> {
                    print(getValue(game, i))
                    println()
                    println(lineDivision())
                    i++
                }
                i == gameSize -> {
                    print(getValue(game, i))
                    println("")
                    break
                }
                else -> {
                    print("${getValue(game, i)}|")
                    i++
                }
            }
        }
    }

    /**
     * Obtains a clear game.
     */
    private fun clearGame() = HashMap<Int, String>(gameSize)

    /**
     * Obtains the spot the player wants to choose.
     * @param game  The game that is being played.
     * @return The spot the player chose.
     */
    private fun getSpot(game: HashMap<Int, String>): Int {
        while (true) {
            val read = readLine()!!.toIntOrNull()
            if (read != null && read >= 1 && read <= gameSize && !game.containsKey(read.toInt())) {
                return read.toInt()
            }
            println("Invalid spot. Try again.")
        }
    }

    /**
     * Checks if someone has one.
     * @param game  The game that is being played.
     * @param spot  The spot chosen by the player.
     * @param fig   The figure the player is using.
     * @return Weather the player has won.
     */
    private fun checkWin(game: HashMap<Int, String>, spot: Int, fig: String): Boolean {
        return when {
            checkColumn(game, spot, fig) -> { println("Column detected."); true }
            checkLine(game, spot, fig) -> { println("Line detected."); true }
            checkDiagonal(game, fig) -> { println("Diagonal detected."); true }
            checkAntiDiagonal(game, fig) -> { println("Anti-diagonal detected."); true }
            else -> false
        }
    }

    /**
     * Checks if there was a draw.
     * @param count The number of spots used until now.
     * @return Weather there has been a draw.
     */
    private fun checkDraw(count: Int): Boolean {
        if (count == gameSize) return true
        return false
    }

    /**
     * Checks the played column for a win situation.
     * @param game  The game that is being played.
     * @param spot  The spot chosen by the player.
     * @param fig   The figure the player is using.
     * @return Weather that column has a winning situation or not.
     */
    private fun checkColumn(game: HashMap<Int, String>, spot: Int, fig: String): Boolean {
        var first = spot
        while (first - n > 0) first -= n
        var last = spot
        while (last + n <= gameSize) last += n
        var i = first
        while (i <= last) {
            if (game[i] != fig) return false
            i += n
        }
        return true
    }

    /**
     * Checks the played line for a win situation.
     * @param game  The game that is being played.
     * @param spot  The spot chosen by the player.
     * @param fig   The figure the player is using.
     * @return Weather that line has a winning situation or not.
     */
    private fun checkLine(game: HashMap<Int, String>, spot: Int, fig: String): Boolean {
        var first = spot
        while ((first - 1) % n > 0) first--
        var last = spot
        while (last % n > 0) last++
        var i = first
        while (i <= last) {
            if (game[i] != fig) return false
            i++
        }
        return true
    }

    /**
     * Checks the diagonal for a win situation.
     * @param game  The game that is being played.
     * @param fig   The figure the player is using.
     * @return Weather the diagonal has a winning situation or not.
     */
    private fun checkDiagonal(game: HashMap<Int, String>, fig: String): Boolean {
        var i = 1
        while (i <= gameSize) {
            if (game[i] != fig) return false
            i += n + 1
        }
        return true
    }

    /**
     * Checks the anti-diagonal for a win situation.
     * @param game  The game that is being played.
     * @param fig   The figure the player is using.
     * @return Weather the anti-diagonal has a winning situation or not.
     */
    private fun checkAntiDiagonal(game: HashMap<Int, String>, fig: String): Boolean {
        var i = n
        while (i < gameSize) {
            if (game[i] != fig) return false
            i += n - 1
        }
        return true
    }

    /**
     * Checks if the user wants to play another game.
     */
    private fun maybeStartNewGame() {
        println("Start new game? (y/n)")
        while (true) {
            when (readLine()) {
                "n" -> exitProcess(0)
                "y" -> break
                else -> println("Invalid answer.")
            }
        }
    }

    fun game() {
        while (true) {
            n = 3
            println("Choose the size of the board (between 3 and 30).")
            val read = readLine()!!.toIntOrNull()
            if (read != null && read >= 3 && read <= 30) {
                n = read
                println("Starting a ${n}X${n} game...")
            }
            else println("Invalid size. Starting a default game...")
            gameSize = n * n
            var count = 0 //Keep track of number of spots used.
            val game = clearGame()
            printGame(game)
            while (true) {
                println("Choose spot for 'X'")
                val xSpot = getSpot(game)
                game[xSpot] = PLAYED_X
                count++
                gameDivision()
                printGame(game)
                if (checkWin(game, xSpot, PLAYED_X)) {
                    println("X wins.")
                    maybeStartNewGame()
                    break
                }
                if (checkDraw(count)) {
                    println("Draw.")
                    maybeStartNewGame()
                    break
                }
                println("Choose spot for 'O'")
                val oSpot = getSpot(game)
                game[oSpot] = PLAYED_O
                count++
                gameDivision()
                printGame(game)
                if (checkWin(game, oSpot, PLAYED_O)) {
                    println("O wins.")
                    maybeStartNewGame()
                    break
                }
                if (checkDraw(count)) {
                    println("Draw.")
                    maybeStartNewGame()
                    break
                }
            }
        }
    }
}

fun main() {
    TicTacToe.game()

}