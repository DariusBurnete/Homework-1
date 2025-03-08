data class Monom(val coef: Int, val exx: Int, val exy: Int)

typealias Polinom = MutableList<Monom>

fun Polinom.reducere(): Polinom {
    val grouped = this.groupBy { Pair(it.exx, it.exy) }
        .mapValues { entry -> entry.value.sumOf { it.coef } }
        .filterValues { it != 0 }
        .map { Monom(it.value, it.key.first, it.key.second) }

    return grouped.toMutableList()
}

infix fun Polinom.plus(other: Polinom): Polinom {
    return (this + other).toMutableList().reducere()
}

infix fun Polinom.ori(other: Polinom): Polinom {
    val result = mutableListOf<Monom>()
    for (monom1 in this) {
        for (monom2 in other) {
            result.add(
                Monom(
                    monom1.coef * monom2.coef,
                    monom1.exx + monom2.exx,
                    monom1.exy + monom2.exy
                )
            )
        }
    }
    return result.reducere()
}

fun Polinom.afisare(): String {
    return this.joinToString("+") { monom ->
        val coefStr = if (monom.coef == 1 && monom.exx == 0 && monom.exy == 0) "1" else if (monom.coef == 1) "" else "${monom.coef}"
        val xStr = if (monom.exx > 0) "x" + if (monom.exx > 1) "^${monom.exx}" else "" else ""
        val yStr = if (monom.exy > 0) "y" + if (monom.exy > 1) "^${monom.exy}" else "" else ""
        "$coefStr$xStr$yStr"
    }
}

fun main() {
    val P: Polinom = mutableListOf(
        Monom(2, 2, 1),
        Monom(5, 2, 0),
        Monom(3, 0, 2),
        Monom(1, 1, 1)
    )

    val Q: Polinom = mutableListOf(
        Monom(7, 2, 1),
        Monom(1, 2, 0),
        Monom(2, 1, 2),
        Monom(3, 1, 1)
    )

    val suma = P plus Q
    val produs = P ori Q

    println("P(x, y) = ${P.afisare()}")
    println("Q(x, y) = ${Q.afisare()}")
    println("P + Q = ${suma.afisare()}")
    println("P * Q = ${produs.afisare()}")
}
