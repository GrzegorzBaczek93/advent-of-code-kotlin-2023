package day03

import readInput
import utils.isDot
import utils.multiply
import utils.walkIndexed
import utils.withStopwatch

fun main() {
    val testInput = readInput("input_03_test")
    withStopwatch { println(part1(testInput)) }
    withStopwatch { println(part2(testInput)) }

    val input = readInput("input_03")
    withStopwatch { println(part1(input)) }
    withStopwatch { println(part2(input)) }
}

private fun List<String>.findCharacter(
    startX: Int,
    endX: Int,
    startY: Int,
    endY: Int,
    condition: (Char) -> Boolean,
): Boolean {
    (startY..endY).forEach { y ->
        (startX..endX).forEach { x ->
            if (condition(this[y][x])) {
                return true
            }
        }
    }
    return false
}

private fun part1(input: List<String>): Int {
    var result = 0
    var foundNumber = ""
    var startX = -1
    var endX = -1
    var startY = -1
    var endY = -1

    fun findAdjacentCharacter() {
        if (foundNumber.isEmpty()) return

        if (input.findCharacter(startX, endX, startY, endY) { char -> !char.isDot() && !char.isDigit() }) {
            val number = foundNumber.toInt()
            result += number
        }
        foundNumber = ""
    }

    input.walkIndexed { x, y, c ->
        when {
            c.isDigit() -> {
                if (foundNumber.isEmpty()) {
                    startX = if (x != 0) x - 1 else 0
                    startY = if (y != 0) y - 1 else 0
                }
                foundNumber += c
                endX = if (x != input.first().length - 1) x + 1 else x

                if (x == input.first().length - 1) {
                    findAdjacentCharacter()
                }
            }

            c.isDot() -> {
                endY = if (y != input.size - 1) y + 1 else y
                findAdjacentCharacter()
            }

            else -> {
                endY = if (y != input.size - 1) y + 1 else y
                findAdjacentCharacter()
            }
        }
    }
    return result
}

private fun part2(input: List<String>): Long {
    var result = 0L

    fun List<String>.checkLeft(x: Int, y: Int): String {
        var foundNumber = ""

        (x - 1 downTo 0).forEach { ix ->
            val c = this[y][ix]
            when {
                c.isDigit() -> {
                    foundNumber = c + foundNumber
                    if (ix == 0) return foundNumber
                }
                else -> return if (foundNumber.isNotEmpty()) foundNumber else "-1"
            }
        }
        return "-1"
    }

    fun List<String>.checkRight(x: Int, y: Int): String {
        var foundNumber = ""

        (x + 1 until this.first().length).forEach { ix ->
            val c = this[y][ix]
            when {
                c.isDigit() -> {
                    foundNumber += c
                    if (ix == this.first().length - 1) return foundNumber
                }
                else -> return if (foundNumber.isNotEmpty()) foundNumber else "-1"
            }
        }
        return "-1"
    }

    fun List<String>.checkBottom(x: Int, y: Int): List<Int> {
        if (y == this.size - 1) return listOf(-1)

        var foundNumber = ""

        return if (this[y + 1][x].isDigit()) {
            foundNumber += this[y + 1][x]
            val left = checkLeft(x, y + 1)
            val right = checkRight(x, y + 1)
            if (left != "-1") foundNumber = "$left$foundNumber"
            if (right != "-1") foundNumber = "${foundNumber}$right"
            listOf(foundNumber.toInt())
        } else {
            val left = checkLeft(x, y + 1).toInt()
            val right = checkRight(x, y + 1).toInt()
            listOf(left, right).filter { it != -1 }
        }
    }

    fun List<String>.checkTop(x: Int, y: Int): List<Int> {
        if (y == 0) return listOf(-1)

        var foundNumber = ""

        return if (this[y - 1][x].isDigit()) {
            foundNumber += this[y - 1][x]
            val left = checkLeft(x, y - 1)
            val right = checkRight(x, y - 1)
            if (left != "-1") foundNumber = "$left$foundNumber"
            if (right != "-1") foundNumber = "$foundNumber$right"
            listOf(foundNumber.toInt())
        } else {
            val left = checkLeft(x, y - 1).toInt()
            val right = checkRight(x, y - 1).toInt()
            listOf(left, right).filter { it != -1 }
        }
    }
    input.walkIndexed { x, y, c ->
        if (c == '*') {
            val foundNumbers: List<Int> = (listOf(input.checkLeft(x, y).toInt(), input.checkRight(x, y).toInt()) + input.checkTop(x, y) + input.checkBottom(x, y)).filter { it != -1 }
            if (foundNumbers.size == 2) {
                result += foundNumbers.multiply()

            }
        }
    }

    return result
}
