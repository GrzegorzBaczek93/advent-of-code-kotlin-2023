package day04

import readInput
import utils.getOrPut
import utils.putOrIncreaseBy
import utils.withStopwatch
import kotlin.math.pow

fun main() {
    val testInput = readInput("input_04_test")
    withStopwatch { println(part1(testInput)) }
    withStopwatch { println(part2(testInput)) }

    val input = readInput("input_04")
    withStopwatch { println(part1(input)) }
    withStopwatch { println(part2(input)) }
}

private fun part1(input: List<String>): Int {
    return input.sumOf { card -> card.calculateMatches().calculatePoints() }
}

private fun part2(input: List<String>): Int {
    val additionalCards = mutableListOf(0)
    input.forEachIndexed { index, card ->
        val amount = 1 + additionalCards.getOrPut(index, 0)
        val matches = card.calculateMatches()
        repeat(matches) { no -> additionalCards.putOrIncreaseBy(index + no + 1, amount) }
    }
    return input.size + additionalCards.sum()
}

private fun String.calculateMatches(): Int {
    val numbers = this.split(':').last().split(' ').filterNot { it.isBlank() || it == "|" }
    return numbers.count() - numbers.toSet().count()
}

private fun Int.calculatePoints(): Int {
    return if (this == 1) 1 else 2.0f.pow(this - 1).toInt()
}
