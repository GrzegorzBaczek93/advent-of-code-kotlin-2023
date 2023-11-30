package utils

fun String.toRange(): IntRange =
    split("-").let { (first, second) -> Integer.parseInt(first)..Integer.parseInt(second) }

fun String.containsDuplicates(): Boolean = toSet().size != this.length
