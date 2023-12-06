package day05

data class Line(
    private val destinationRangeStart: Long,
    private val sourceRangeStart: Long,
    private val rangeLength: Long
) {
    fun getDestinationOrNull(source: Long): Long? {
        return if ((sourceRangeStart..< sourceRangeStart + rangeLength).contains(source)) {
            source - sourceRangeStart + destinationRangeStart
        } else {
            null
        }
    }

    companion object {
        fun List<Long>.toLine(): Line {
            val (destinationRangeStart, sourceRangeStart, rangeLength) = this
            return Line(
                destinationRangeStart = destinationRangeStart,
                sourceRangeStart = sourceRangeStart,
                rangeLength = rangeLength,
            )
        }
    }
}
