import java.io.File

/**
 * Reads lines from the given input_01.txt file.
 */
fun readInput(name: String) = File("src/day${name.filter { it.isDigit() }}", "$name.txt").readLines()
