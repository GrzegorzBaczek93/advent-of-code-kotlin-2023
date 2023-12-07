package day06

import readInput
import utils.withStopwatch
import kotlin.math.*

fun main() {
    val testInput = readInput("input_06_test")
    withStopwatch { println(part1(testInput)) }
    withStopwatch { println(part2(testInput)) }

    val input = readInput("input_06")
    withStopwatch { println(part1(input)) }
    withStopwatch { println(part2(input)) }
}

private fun part1(input: List<String>): Long {
    val (time, distance) = input.map { line -> line.split(" ").filter { it.isNotBlank() }.drop(1).map { it.toLong() } }
    var result: Long = 1
    time.indices.forEach { i ->
        val (min, max) = calculateMinMax(time[i], distance[i])
        result *= max - min + 1
    }
    return result
}

private fun part2(input: List<String>): Long {
    val (time, distance) = input.map { line -> line.filter { it.isDigit() }.toLong() }
    val (min, max) = calculateMinMax(time, distance)
    return max - min + 1
}

private fun calculateMinMax(time: Long, minDistance: Long): Pair<Long, Long> {
    val delta = (time.toDouble()).pow(2) - 4 * minDistance
    var x1 = ((time - sqrt(delta)) / 2)
    var x2 = ((time + sqrt(delta)) / 2)
    x1 = if (x1 % 10 == 0.0) x1 + 1.0 else x1
    x2 = if (x2 % 10 == 0.0) x2 - 1.0 else x2
    return Pair(ceil(x1).toLong(), floor(x2).toLong())
}
