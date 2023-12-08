package day08

import readInput
import utils.lcm
import utils.withStopwatch
import kotlin.math.*

fun main() {
    val testInput = readInput("input_08_test")
//    withStopwatch { println(part1(testInput)) }
//    withStopwatch { println(part2(testInput)) }

    val input = readInput("input_08")
    withStopwatch { println(part1(input)) }
    withStopwatch { println(part2(input)) }
}

private fun part1(input: List<String>): Long {
    val instruction = input.first()

    val nodes = input.subList(2, input.size).associate { line ->
        line.substring(0..2) to Pair(line.substring(7..9), line.substring(12..14))
    }

    return calculateLength("AAA", { it != "ZZZ" }, nodes, instruction)
}

private fun part2(input: List<String>): Long {
    val instruction = input.first()

    val nodes = input.subList(2, input.size).associate { line ->
        line.substring(0..2) to Pair(line.substring(7..9), line.substring(12..14))
    }

    val results = nodes.filterKeys { it.endsWith('A') }.keys.map { key ->
        calculateLength(key, { !it.endsWith('Z') }, nodes, instruction)
    }

    return lcm(results)
}

private fun calculateLength(
    startNode: String,
    condition: (String) -> Boolean,
    nodes: Map<String, Pair<String, String>>,
    instruction: String,
): Long {
    var instructionIndex = 0
    var currentNode = startNode
    var counter = 0L

    while (condition(currentNode)) {
        when (instruction[instructionIndex]) {
            'R' -> currentNode = nodes[currentNode]!!.second
            'L' -> currentNode = nodes[currentNode]!!.first
        }
        instructionIndex = if (instructionIndex == instruction.lastIndex) 0 else instructionIndex + 1
        counter++
    }

    return counter
}
