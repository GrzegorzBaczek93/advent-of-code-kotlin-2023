package day07

import day07.Hand.Companion.toHand
import readInput
import utils.sumOfIndexed
import utils.withStopwatch

fun main() {
    val testInput = readInput("input_07_test")
    withStopwatch { println(part1(testInput)) }
    withStopwatch { println(part2(testInput)) }

    val input = readInput("input_07")
    withStopwatch { println(part1(input)) }
    withStopwatch { println(part2(input)) }
}

private fun part1(input: List<String>): Long {
    return input.map { line -> line.toHand() }.sorted().calculateWinnings()
}

private fun part2(input: List<String>): Long {
    return input.map { line -> line.toHand() }.sorted().calculateWinnings()
}

private fun List<Hand>.calculateWinnings(): Long {
    return sumOfIndexed { index: Int, hand: Hand ->
        hand.bid * (index + 1).toLong()
    }
}
