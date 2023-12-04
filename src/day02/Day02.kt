package day02

import readInput
import utils.withStopwatch

fun main() {
    val testInput = readInput("input_02_test")
    withStopwatch { println(part1(testInput)) }
    withStopwatch { println(part2(testInput)) }

    val input = readInput("input_02")
    withStopwatch { println(part1(input)) }
    withStopwatch { println(part2(input)) }
}

private fun part1(input: List<String>): Int {
    val limit = GameSet(red = 12, green = 13, blue = 14)

    return input.foldIndexed(0) { index, acc, record ->
        record.splitToGameSets().forEach { if (!GameSet.from(it).isPossible(limit)) return@foldIndexed acc }
        acc + index + 1
    }
}

private fun part2(input: List<String>): Int {
    return input.fold(0) { acc, record ->
        acc + record.splitToGameSets().map { GameSet.from(it) }.calculatePower()
    }
}

private fun String.splitToGameSets() = split(":").last().split(";")

fun List<GameSet>.calculatePower(): Int {
    var minRed = 0
    var minBlue = 0
    var minGreen = 0

    forEach {
        if (it.red > minRed) minRed = it.red
        if (it.blue > minBlue) minBlue = it.blue
        if (it.green > minGreen) minGreen = it.green
    }
    return minRed * minBlue * minGreen
}

data class GameSet(
    val red: Int = 0,
    val blue: Int = 0,
    val green: Int = 0,
) {
    fun isPossible(limit: GameSet): Boolean {
        return red <= limit.red && blue <= limit.blue && green <= limit.green
    }

    companion object {
        private fun List<String>.getNumber(color: String) =
            firstOrNull { it.contains(color) }?.filter { it.isDigit() }?.toInt() ?: 0

        fun from(game: String): GameSet {
            val parts = game.split(",")
            return GameSet(
                red = parts.getNumber("red"),
                blue = parts.getNumber("blue"),
                green = parts.getNumber("green"),
            )
        }
    }
}