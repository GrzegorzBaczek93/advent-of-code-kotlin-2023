package day05

import day05.Almanac.Companion.toAlmanac
import readInput
import utils.*

fun main() {
    val testInput = readInput("input_05_test")
    withStopwatch { println(part1(testInput)) }
//    withStopwatch { println(part2(testInput)) }

    val input = readInput("input_05")
    withStopwatch { println(part1(input)) }
//    withStopwatch { println(part2(input)) }
}

private fun part1(input: List<String>): Long {
    val almanac = input.toAlmanac()
    return input.first().split(" ").drop(1).map { it.toLong() }.minOf { seed ->
        almanac.getLocation(seed)
    }
}

private fun part2(input: List<String>): Long {
    return 0
}

