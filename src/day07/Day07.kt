package day07

import day07.Card.Companion.toCard
import day07.Hand.Companion.isFullHouse
import day07.Hand.Companion.toHand
import readInput
import utils.sumOfIndexed
import utils.withStopwatch

fun main() {
    val testInput = readInput("input_07_test")
    withStopwatch { println(part1(testInput)) }
//    withStopwatch { println(part2(testInput)) }

    val input = readInput("input_07")
    withStopwatch { println(part1(input)) }
//    withStopwatch { println(part2(input)) }
}

private fun part1(input: List<String>): Long {
    return input.map { line -> line.toHand() }.sorted().calculateWinnings()
}

private fun part2(input: List<String>): Long {
    return 0
}

private fun List<Hand>.calculateWinnings(): Long {
    return sumOfIndexed { index: Int, hand: Hand ->
        hand.bid * (index + 1).toLong()
    }
}

private sealed class Hand(
    private val weight: Int,
    open val cards: List<Card>,
    open val bid: Int,
) : Comparable<Hand> {

    data class FiveOfAKind(override val cards: List<Card>, override val bid: Int) : Hand(7, cards, bid)

    data class FourOfAKind(override val cards: List<Card>, override val bid: Int) : Hand(6, cards, bid)

    data class FullHouse(override val cards: List<Card>, override val bid: Int) : Hand(5, cards, bid)

    data class ThreeOfAKind(override val cards: List<Card>, override val bid: Int) : Hand(4, cards, bid)

    data class TwoPair(override val cards: List<Card>, override val bid: Int) : Hand(3, cards, bid)

    data class OnePair(override val cards: List<Card>, override val bid: Int) : Hand(2, cards, bid)

    data class HighCard(override val cards: List<Card>, override val bid: Int) : Hand(1, cards, bid)

    override fun compareTo(other: Hand): Int {
        return when {
            this.weight > other.weight -> 1
            this.weight < other.weight -> -1
            else -> this.compareByHighCard(other)
        }
    }

    private fun Hand.compareByHighCard(other: Hand): Int {
        (0..4).forEach {
            when {
                this.cards[it] > other.cards[it] -> return 1
                this.cards[it] < other.cards[it] -> return -1
            }
        }
        return 0
    }

    companion object {
        fun String.toHand(): Hand {
            val parts = split(" ")
            val cards = parts.first().map { it.toCard() }
            val bid = parts.last().toInt()

            return cards.isFiveOfAKind(bid) ?: cards.isFourOfAKind(bid) ?: cards.isFullHouse(bid)
            ?: cards.isThreeOfAKind(bid) ?: cards.isTwoPair(bid) ?: cards.isOnePair(bid) ?: HighCard(cards, bid)
        }

        private fun List<Card>.isFiveOfAKind(bid: Int): Hand? {
            return if (this.groupBy { it }.map { it.value }.any { it.size == 5 }) FiveOfAKind(this, bid) else null
        }

        private fun List<Card>.isFourOfAKind(bid: Int): Hand? {
            return if (this.groupBy { it }.map { it.value }.any { it.size == 4 }) FourOfAKind(this, bid) else null
        }

        private fun List<Card>.isFullHouse(bid: Int): Hand? {
            return if (this.groupBy { it }.map { it.value }.size == 2) FullHouse(this, bid) else null
        }

        private fun List<Card>.isThreeOfAKind(bid: Int): Hand? {
            return if (this.groupBy { it }.map { it.value }.any { it.size == 3 }) ThreeOfAKind(this, bid) else null
        }

        private fun List<Card>.isTwoPair(bid: Int): Hand? {
            return if (this.groupBy { it }.map { it.value }.count { it.size == 2 } == 2) TwoPair(this, bid) else null
        }

        private fun List<Card>.isOnePair(bid: Int): Hand? {
            return if (this.groupBy { it }.map { it.value }.any { it.size == 2 }) OnePair(this, bid) else null
        }
    }
}

private enum class Card(
    private val char: Char,
) : Comparable<Card> {
    Two('2'),
    Three('3'),
    Four('4'),
    Five('5'),
    Six('6'),
    Seven('7'),
    Eight('8'),
    Nine('9'),
    T('T'),
    J('J'),
    Q('Q'),
    K('K'),
    A('A');

    override fun toString(): String {
        return char.toString()
    }

    companion object {
        fun Char.toCard(): Card = entries.first { it.char == this }
    }
}
