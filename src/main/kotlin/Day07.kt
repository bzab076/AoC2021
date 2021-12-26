import kotlin.math.abs

class Day07 : AbstractDay(7) {

    override fun partOne(): Number {

        val positions = inputString().split(",").map { it.toInt() }
        val maxPos = positions.maxOf { it }

        val possibleFuels = (0..maxPos).map {
            positions.sumOf { pos -> abs(pos - it) }
        }

        return possibleFuels.minOrNull() as Int
    }

    override fun partTwo(): Number {

        val positions = inputString().split(",").map { it.toInt() }
        val maxPos = positions.maxOf { it }

        val possibleFuels = (0..maxPos).map {
            positions.sumOf { pos -> (abs(pos - it) * (abs(pos - it) + 1) / 2) }
        }

        return possibleFuels.minOrNull() as Int
    }
}