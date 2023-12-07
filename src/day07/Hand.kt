package day07

import day07.Card.Companion.toCard

sealed class Hand(
    private val weight: Int,
    open val cards: List<Card>,
    open val originalCards: List<Card>,
    open val bid: Int,
) : Comparable<Hand> {

    data class FiveOfAKind(
        override val cards: List<Card>,
        override val bid: Int,
        override val originalCards: List<Card>,
    ) : Hand(7, cards, originalCards, bid)

    data class FourOfAKind(
        override val cards: List<Card>,
        override val bid: Int,
        override val originalCards: List<Card>,
    ) : Hand(6, cards, originalCards, bid)

    data class FullHouse(
        override val cards: List<Card>,
        override val bid: Int,
        override val originalCards: List<Card>,
    ) : Hand(5, cards, originalCards, bid)

    data class ThreeOfAKind(
        override val cards: List<Card>,
        override val bid: Int,
        override val originalCards: List<Card>,
    ) : Hand(4, cards, originalCards, bid)

    data class TwoPair(
        override val cards: List<Card>,
        override val bid: Int,
        override val originalCards: List<Card>,
    ) : Hand(3, cards, originalCards, bid)

    data class OnePair(
        override val cards: List<Card>,
        override val bid: Int,
        override val originalCards: List<Card>,
    ) : Hand(2, cards, originalCards, bid)

    data class HighCard(
        override val cards: List<Card>,
        override val bid: Int,
        override val originalCards: List<Card>,
    ) : Hand(1, cards, originalCards, bid)

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
                this.originalCards[it] > other.originalCards[it] -> return 1
                this.originalCards[it] < other.originalCards[it] -> return -1
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
            ?: cards.isThreeOfAKind(bid) ?: cards.isTwoPair(bid) ?: cards.isOnePair(bid) ?: cards.getHighCard(bid)
        }

        private fun List<Card>.isFiveOfAKind(bid: Int): Hand? {
            return if (this.groupBy { it }.map { it.value }.any { it.size == 5 }) {
                if (contains(Card.J)) {
                    FiveOfAKind(map { Card.A }, bid, this)
                } else {
                    FiveOfAKind(this, bid, this)
                }
            } else null
        }

        private fun List<Card>.isFourOfAKind(bid: Int): Hand? {
            return if (this.groupBy { it }.map { it.value }.any { it.size == 4 }) {
                if (contains(Card.J)) {
                    FiveOfAKind(List(5) { getHighestCard() }, bid, this)
                } else {
                    FourOfAKind(this, bid, this)
                }
            } else null
        }

        private fun List<Card>.isFullHouse(bid: Int): Hand? {
            return if (this.groupBy { it }.map { it.value }.size == 2) {
                if (contains(Card.J)) {
                    FiveOfAKind(List(5) { getHighestCard() }, bid, this)
                } else {
                    FullHouse(this, bid, this)
                }
            } else null
        }

        private fun List<Card>.isThreeOfAKind(bid: Int): Hand? {
            return if (this.groupBy { it }.map { it.value }.any { it.size == 3 }) {
                if (contains(Card.J)) {
                    val card = if (getJokersAmount() == 1) {
                        this.groupBy { it }.map { it.value }.first { it.size == 3 }.first()
                    } else getHighestCard()
                    FourOfAKind(map { if (it == Card.J) card else it }, bid, this)
                } else {
                    ThreeOfAKind(this, bid, this)
                }
            } else null
        }

        private fun List<Card>.isTwoPair(bid: Int): Hand? {
            return if (this.groupBy { it }.map { it.value }.count { it.size == 2 } == 2) {
                if (contains(Card.J)) {
                    val card = this.groupBy { it }.map { it.value }.first { it.size == 2 }.first()
                    if (getJokersAmount() == 2) {
                        FourOfAKind(map { if (it == Card.J) card else it }, bid, this)
                    } else FullHouse(map { if (it == Card.J) card else it }, bid, this)
                } else TwoPair(this, bid, this)
            } else null
        }

        private fun List<Card>.isOnePair(bid: Int): Hand? {
            return if (this.groupBy { it }.map { it.value }.any { it.size == 2 }) {
                if (contains(Card.J)) {
                    val card = if (getJokersAmount() == 1) {
                        this.groupBy { it }.map { it.value }.first { it.size == 2 }.first()
                    } else getHighestCard()
                    ThreeOfAKind(map { if (it == Card.J) card else it }, bid, this)
                } else OnePair(this, bid, this)
            } else null
        }

        private fun List<Card>.getHighCard(bid: Int): Hand {
            return if (contains(Card.J)) {
                OnePair(map { if (it == Card.J) getHighestCard() else it }, bid, this)
            } else HighCard(this, bid, this)
        }

        private fun List<Card>.getHighestCard() = maxOf { it }

        private fun List<Card>.getJokersAmount() = count { it == Card.J }
    }
}
