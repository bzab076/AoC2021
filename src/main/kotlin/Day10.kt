class Day10 : AbstractDay(10) {

    override fun partOne(): Number {

        return inputLines().sumOf { getCorruptionScore(it) }
    }

    override fun partTwo(): Number {

        val scores = inputLines().filter { getCorruptionScore(it) == 0 }.map { getCompletionScore(it) }.sorted()
        return scores[(scores.size - 1) / 2]
    }

    private fun getCorruptionScore(line : String) : Int {

        var corruptionScore = 0
        val openBrackets : MutableList<Char> = emptyList<Char>().toMutableList()

        line.forEach {

            if(listOf('[','{','(','<').contains(it)) {
                openBrackets.add(it)
            }
            else {
                when (it) {
                    ')' -> if(openBrackets.last() != '(')  corruptionScore = 3
                    ']' -> if(openBrackets.last() != '[')  corruptionScore = 57
                    '}' -> if(openBrackets.last() != '{')  corruptionScore = 1197
                    '>' -> if(openBrackets.last() != '<')  corruptionScore = 25137
                }
                openBrackets.removeLast()
            }

            if(corruptionScore>0) {
                return corruptionScore
            }
        }

        return 0 // if line is not corrupted we come to here
    }

    private fun getCompletionScore(line : String) : Long {

        val openBrackets : MutableList<Char> = emptyList<Char>().toMutableList()

        line.forEach {
            if(listOf('[','{','(','<').contains(it)) {
                openBrackets.add(it)
            }
            else {
                openBrackets.removeLast()
            }
        }

        val closingBrackets = openBrackets.reversed().map{
            when (it) {
                '(' -> ')'
                '[' -> ']'
                '{' -> '}'
                '<' -> '>'
                else -> '.' // must not happen
            }
        }

        val score = closingBrackets.fold(0L) { acc, it ->
            val charScore = when (it) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> 0
            }
            acc*5 + charScore
        }

        return score
    }
}