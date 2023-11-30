package day01

import readInput
import utils.withStopwatch

fun main() {
    val testInput = readInput("input_01_test")
    withStopwatch { println(part1(testInput)) }
    withStopwatch { println(part2(testInput)) }

    val input = readInput("input_01")
    withStopwatch { println(part1(input)) }
    withStopwatch { println(part2(input)) }
}

private fun part1(input: List<String>) = 0

private fun part2(input: List<String>) = 0
