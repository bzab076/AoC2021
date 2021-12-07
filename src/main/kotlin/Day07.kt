import kotlin.math.abs

class Day07 : AbstractDay(7) {

    override fun partOne(): Number {

        val positions = inputString().split(",").map { it -> it.toInt() }
        val maxPos = positions.map { it -> it.toInt() }.maxOrNull() as Int

        val possibleFuels = (0..maxPos).map {
                it -> positions.fold(0) { acc, pos ->
                   acc + abs(pos-it)
             }
        }

        val res = possibleFuels.minOrNull() as Int

        return res
    }

    override fun partTwo(): Number {

        val positions = inputString().split(",").map { it -> it.toInt() }
        val maxPos = positions.map { it -> it.toInt() }.maxOrNull() as Int

        val possibleFuels = (0..maxPos).map {
                it -> positions.fold(0) { acc, pos ->
                    acc + (abs(pos-it) * (abs(pos-it) + 1) / 2)
                }
        }

        val res = possibleFuels.minOrNull() as Int

        return res
    }
}