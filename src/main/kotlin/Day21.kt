import java.lang.Integer.min

class Day21 : AbstractDay(21) {

    private val player1StartingPosition = 3
    private val player2StartingPosition = 7
    private var deterministicDieResult = 1

    private val quantumDiceResults = mapOf(3 to 1L, 4 to 3L, 5 to 6L, 6 to 7L, 7 to 6L, 8 to 3L, 9 to 1L)
    private val stateCache : MutableMap<GameState, UniverseCount> = mutableMapOf()

    override fun partOne(): Number {

        var p1Score = 0
        var p2Score = 0
        var p1Pos = player1StartingPosition
        var p2Pos = player2StartingPosition
        val maxScore = 1000
        var rollCount = 0
        while(true) {

            val p1Die = rollDeterministicDice()
            rollCount += 3
            p1Pos += p1Die
            p1Pos = (p1Pos - 1) % 10 + 1
            p1Score += p1Pos
            if(p1Score>=maxScore) break

            val p2Die = rollDeterministicDice()
            rollCount += 3
            p2Pos +=p2Die
            p2Pos = (p2Pos - 1) % 10 + 1
            p2Score += p2Pos
            if(p2Score>=maxScore) break
        }

        return min(p1Score,p2Score) * rollCount

    }

    override fun partTwo(): Number {

        return playDiracDice(GameState(player1StartingPosition,0,player2StartingPosition,0, true)).max()
    }

    private fun rollDeterministicDice() : Int {

        var result = deterministicDieResult
        repeat(3){
            deterministicDieResult++
            deterministicDieResult %= 100
            result += deterministicDieResult
        }
        return result - deterministicDieResult
    }

    private fun playDiracDice(state: GameState): UniverseCount =
        when {
            state.p1Score >= 21 || state.p2Score >= 21 ->
                if (state.p1Score > state.p2Score) UniverseCount(1L, 0L) else UniverseCount(0L, 1L)
            state in stateCache ->
                stateCache.getValue(state)
            else ->
                quantumDiceResults.map { (dieValue, frequency) ->
                    playDiracDice(state.nextState(dieValue)) * frequency
                }.fold (UniverseCount(0,0)){acc, it -> acc+it}.also{ stateCache.put(state, it) }
        }

    private class GameState(val p1Pos : Int, val p1Score : Int, val p2Pos : Int, val p2Score : Int, val player1 : Boolean = true) {

        fun nextState(dieValue : Int) : GameState {
            val pos : Int = if(player1) p1Pos else p2Pos
            val score : Int = if(player1) p1Score else p2Score
            val newPos = (pos + dieValue - 1) % 10 + 1
            val newScore = score + newPos
            return if(player1) GameState(newPos, newScore, p2Pos, p2Score, !player1) else GameState(p1Pos, p1Score, newPos, newScore, !player1)
        }

    }

    private class UniverseCount(val p1Count: Long, val p2Count: Long) {

        operator fun plus(other: UniverseCount): UniverseCount =
            UniverseCount(p1Count + other.p1Count, p2Count + other.p2Count)

        operator fun times(multiplier: Long): UniverseCount =
            UniverseCount(p1Count * multiplier, p2Count * multiplier)

        fun max(): Long = maxOf(p1Count, p2Count)

    }


}