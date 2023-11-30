package utils

inline fun <T> List<T>.ifNotEmpty(action: (List<T>) -> Unit) {
    if (isNotEmpty()) action(this)
}

inline fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    val resultList = mutableListOf<List<T>>()
    val buffer = mutableListOf<T>()

    for (element in this) {
        if (predicate(element)) {
            buffer.ifNotEmpty { resultList.add(buffer.toList()) }
            buffer.clear()
        } else {
            buffer.add(element)
        }
    }
    buffer.ifNotEmpty { resultList.add(buffer.toList()) }

    return resultList.toList()
}

/**
 * Removes n last entries from list and return it.
 */
inline fun <T> MutableList<T>.removeLast(n: Int): List<T> {
    if (isEmpty()) {
        return emptyList()
    }
    if (n >= size) {
        val removedItems = this.toList()
        clear()
        return removedItems
    }
    val removedItems = this.takeLast(n)
    repeat(n) { removeLast() }
    return removedItems
}

/**
 * Returns 6th *element* from the list.
 *
 * Throws an [IndexOutOfBoundsException] if the size of this list is less than 6.
 */
inline operator fun <T> List<T>.component6(): T {
    return get(5)
}

/**
 * Returns the multiplication of all elements in the collection.
 */
inline fun List<Int>.multiply(): Int {
    if (this.isEmpty()) return 0

    var sum: Int = 1
    for (element in this) {
        sum *= element
    }
    return sum
}

/**
 * Returns the multiplication of all elements in the collection.
 */
inline fun List<Long>.multiply(): Long {
    if (this.isEmpty()) return 0

    var sum: Long = 1
    for (element in this) {
        sum *= element
    }
    return sum
}

/**
 * For each indexed for two-dimensional
 */
inline fun List<String>.walkIndexed(action: (x: Int, y: Int, c: Char) -> Unit) {
    forEachIndexed { x, internal ->
        internal.forEachIndexed { y, e ->
            action(x, y, e)
        }
    }
}
