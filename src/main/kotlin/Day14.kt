class Day14 : AbstractDay(14) {

    private val rules : Map<String,String> = parseRules()
    private val template = inputLines().first()

    override fun partOne(): Number {
        return growPolymer(10)
    }

    override fun partTwo(): Number {
        return growPolymer(40)
    }

    private fun parseRules() : Map<String,String> {

        val ruleList = inputLines().drop(2).map{it.split(" -> ")}
        val ruleMap : MutableMap<String,String> = mutableMapOf<String,String>()
        ruleList.forEach {
            ruleMap.put(it.first(),it.last())
        }

        return ruleMap
    }

    private fun growPolymer(steps : Int) : Long {

        var pairCounts = template.windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        repeat(steps) {
            pairCounts = nextStep(pairCounts)
        }

        val charCounts = mutableMapOf<Char, Long>().withDefault { 0L }
        pairCounts.forEach { (pair,count) ->
            charCounts.put(pair[0], charCounts.getValue(pair[0]) + count)
            charCounts.put(pair[1], charCounts.getValue(pair[1]) + count)
        }
        // characters were counted twice, except first and last, which is why we add 1 to their count
        charCounts.put(template.first(), charCounts.getValue(template.first()) + 1)
        charCounts.put(template.last(), charCounts.getValue(template.last()) + 1)

        return charCounts.values.maxOf { it / 2 } - charCounts.values.minOf { it / 2 }
    }

    private fun nextStep(pairCounts: Map<String, Long>): Map<String, Long> {

        val newPairCounts = mutableMapOf<String, Long>().withDefault { 0L }
        pairCounts.forEach { (pair, count) ->
            val middle = rules.getValue(pair)
            val pair1 = "${pair[0]}$middle"
            val pair2 = "$middle${pair[1]}"
            newPairCounts.put(pair1, newPairCounts.getValue(pair1) + count)
            newPairCounts.put(pair2, newPairCounts.getValue(pair2) + count)
        }

        return newPairCounts
    }
}