package day01

import readInput
import utils.withStopwatch

private val firstNumberRegex = Regex("""[1-9]|one|two|three|four|five|six|seven|eight|nine|$""")
private val lastNumberRegex = Regex("""[1-9]|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin|$""")

fun main() {
    val testInput = readInput("input_01_test")
//    withStopwatch { println(part1(testInput)) }
    withStopwatch { println(part2(testInput)) }

    val input = readInput("input_01")
//    withStopwatch { println(part1(input)) }
    withStopwatch { println(part2(input)) }
}

private fun part1(input: List<String>): Int {
    return input.fold(0) { acc, line ->
        val first = line.first { it.isDigit() }
        val last = line.last { it.isDigit() }
        acc + "$first$last".toInt()
    }
}

private fun part2(input: List<String>): Int {
    return input.fold(0) { acc, line ->
        val first = line.findFirstNumber()
        val last = line.findLastNumber()
        acc + "$first$last".toInt()
    }
}

private fun String.findFirstNumber(): Int {
    return firstNumberRegex.find(this)?.value.toNumber()
}

private fun String.findLastNumber(): Int {
    return lastNumberRegex.find(this.reversed())?.value?.reversed().toNumber()
}

private fun String?.toNumber(): Int {
    return when(this) {
        null -> -1
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> this.toInt()
    }
}