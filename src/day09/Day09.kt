package day09

import readInput
import utils.split
import utils.withStopwatch

fun main() {
    val testInput = readInput("input_09_test")
    withStopwatch { println(part1(testInput)) }
    withStopwatch { println(part2(testInput)) }

    val input = readInput("input_09")
    withStopwatch { println(part1(input)) }
    withStopwatch { println(part2(input)) }
}

private fun part1(input: List<String>): Long {
    return input.map { line -> line.split(' ').map { it.toLong() } }.sumOf { extrapolateLast(it) }
}

private fun part2(input: List<String>): Long {
    return input.map { line -> line.split(' ').map { it.toLong() } }.sumOf { extrapolateFirst(it) }
}

private fun extrapolateFirst(seq: List<Long>): Long {
    return seq.first() - calculateFirst(seq)
}

private fun extrapolateLast(seq: List<Long>): Long {
   return calculateLast(seq) + seq.last()
}

private fun calculateLast(seq: List<Long>): Long {
    val newSeq = getNextSequence(seq)
    return if (newSeq.all { it == 0L }) 0L else calculateLast(newSeq) + newSeq.last()
}

private fun calculateFirst(seq: List<Long>): Long {
    val newSeq = getNextSequence(seq)
    return if (newSeq.all { it == 0L }) 0L else newSeq.first() - calculateFirst(newSeq)
}

private fun getNextSequence(seq: List<Long>): List<Long> {
    val newSeq = mutableListOf<Long>()
    seq.windowed(2, 1) { (n1, n2) -> newSeq.add(n2 - n1) }
    return newSeq.toList()
}
