package day07

enum class Card(
    private val char: Char,
) : Comparable<Card> {
    J('J'),
    Two('2'),
    Three('3'),
    Four('4'),
    Five('5'),
    Six('6'),
    Seven('7'),
    Eight('8'),
    Nine('9'),
    T('T'),
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
