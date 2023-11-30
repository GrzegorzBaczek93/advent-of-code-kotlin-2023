package utils

inline fun <P1, P2, R> multiLet(p1: P1?, p2: P2?, action: (P1, P2) -> R): R? {
    return if (p1 != null && p2 != null) action(p1, p2) else null
}
