import kotlin.system.exitProcess

/**
 * TicTacToe
 * Author: Alexandre Madeira
 * Description: Console-based TicTacToe where you can play with different-sized boards based on the value of N.
 */

const val N = 3 //Has to be between 3 and 30.
const val GAME_SIZE = N * N
const val playedX = "  X  "
const val playedO = "  O  "

/**
 * Obtains the value of the given spot.
 * @param game  The current game.
 * @param key   The number of the spot.
 * @return The value of the given spot.
 */
fun getValue(game: HashMap<Int, String>, key: Int) = if (!game.containsKey(key)) getSpot(key) else game[key]

/**
 * Obtains a string that will represent the spot on the board.
 * @param n The number that will be written.
 * @return The string that represents the spot.
 */
fun getSpot(n: Int): String {
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
fun lineDivision(): String {
    var str = "-----"
    repeat(6 * N - 6) {
        str += "-"
    }
    return str
}

/**
 * Prints a division with a reasonable size between different instances of the same game.
 */
fun gameDivision() {
    var str = "====="
    repeat(6 * N - 6) {
        str += "="
    }
    str += str
    println(str)
}

/**
 * Prints the given game.
 * @param game  The game that is going to be printed.
 */
fun printGame(game: HashMap<Int, String>) {
    var i = 1
    while (i <= GAME_SIZE) {
        when {
            (i - 1) % N == 0 -> {
                print("${getValue(game, i)}|")
                i++
            }
            i < GAME_SIZE && i % N == 0 -> {
                print(getValue(game, i))
                println()
                println(lineDivision())
                i++
            }
            i == GAME_SIZE -> {
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
fun clearGame() = HashMap<Int, String>(GAME_SIZE)

/**
 * Obtains the spot the player wants to choose.
 * @param game  The game that is being played.
 * @return The spot the player chose.
 */
fun getSpot(game: HashMap<Int, String>): Int {
    while (true) {
        val read = readLine()!!.toIntOrNull()
        if (read != null && read >= 1 && read <= GAME_SIZE && !game.containsKey(read.toInt())) {
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
fun checkWin(game: HashMap<Int, String>, spot: Int, fig: String): Boolean {
    return when {
        checkColumn(game, spot, fig) -> { println("Column detected."); true }
        checkLine(game, spot, fig) -> { println("Line detected."); true }
        checkDiag(game, fig) -> { println("Diagonal detected."); true }
        checkAntiDiag(game, fig) -> { println("Anti-diagonal detected."); true }
        else -> false
    }
}

/**
 * Checks if there was a draw.
 * @param count The number of spots used until now.
 * @return Weather there has been a draw.
 */
fun checkDraw(count: Int): Boolean {
    if (count == GAME_SIZE) return true
    return false
}

/**
 * Checks the played column for a win situation.
 * @param game  The game that is being played.
 * @param spot  The spot chosen by the player.
 * @param fig   The figure the player is using.
 * @return Weather that column has a winning situation or not.
 */
fun checkColumn(game: HashMap<Int, String>, spot: Int, fig: String): Boolean {
    var first = spot
    while (first - N > 0) first -= N
    var last = spot
    while (last + N <= GAME_SIZE) last += N
    var i = first
    while (i <= last) {
        if (game[i] != fig) return false
        i += N
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
fun checkLine(game: HashMap<Int, String>, spot: Int, fig: String): Boolean {
    var first = spot
    while ((first - 1) % N > 0) first--
    var last = spot
    while (last % N > 0) last++
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
fun checkDiag(game: HashMap<Int, String>, fig: String): Boolean {
    var i = 1
    while (i <= GAME_SIZE) {
        if (game[i] != fig) return false
        i += N + 1
    }
    return true
}

/**
 * Checks the anti-diagonal for a win situation.
 * @param game  The game that is being played.
 * @param fig   The figure the player is using.
 * @return Weather the anti-diagonal has a winning situation or not.
 */
fun checkAntiDiag(game: HashMap<Int, String>, fig: String): Boolean {
    var i = N
    while (i < GAME_SIZE) {
        if (game[i] != fig) return false
        i += N - 1
    }
    return true
}

/**
 * Checks if the user wants to play another game.
 */
fun maybeStartNewGame() {
    println("Start new game? (y/n)")
    while (true) {
        when (readLine()) {
            "n" -> exitProcess(0)
            "y" -> break
            else -> println("Invalid answer.")
        }
    }
}

fun main() {
    if (N < 3 || N > 30) {
        println("Invalid game size.")
        exitProcess(0)
    }
    while (true) {
        var count = 0 //Keep track of number of spots used.
        val game = clearGame()
        printGame(game)
        while (true) {
            println("Choose spot for 'X'")
            val xSpot = getSpot(game)
            game[xSpot] = playedX
            count++
            gameDivision()
            printGame(game)
            if (checkWin(game, xSpot, playedX)) {
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
            game[oSpot] = playedO
            count++
            gameDivision()
            printGame(game)
            if (checkWin(game, xSpot, playedO)) {
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