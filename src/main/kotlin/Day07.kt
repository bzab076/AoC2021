import kotlin.math.abs

class Day07 : AbstractDay(7) {

    override fun partOne(): Number {

        val positions = inputString().split(",").map { it -> it.toInt() }
        val maxPos = positions.maxOf { it -> it }

        val possibleFuels = (0..maxPos).map {
                it -> positions.sumOf { pos -> abs(pos - it) }
        }

        val res = possibleFuels.minOrNull() as Int

        return res
    }

    override fun partTwo(): Number {

        val positions = inputString().split(",").map { it -> it.toInt() }
        val maxPos = positions.maxOf { it -> it }

        val possibleFuels = (0..maxPos).map {
                it -> positions.sumOf { pos -> (abs(pos-it) * (abs(pos-it) + 1) / 2) }
        }

        val res = possibleFuels.minOrNull() as Int

        return res
    }
}