class Day06 : AbstractDay(6) {

    private val results : MutableMap<Int, Long> = emptyMap<Int,Long>().toMutableMap()

    override fun partOne(): Number {

        // get number of descendants for every fish in the list and sum them into the result
        return inputString().split(",").sumOf { countFishOptimized(it.toInt(), 80) }
    }

    override fun partTwo(): Number {

        // get number of descendants for every fish in the list and sum them into the result
        return inputString().split(",").sumOf { countFishOptimized(it.toInt(), 256) }
    }


    /*
    * Counts fish with initial timer value and all its descendants
     */
    private fun countFish(timer : Int, startDay: Int, endDay : Int) : Long {

        var fishCount = 1L
        var fishNumber = timer

        for(d in startDay..endDay) {
            if(fishNumber == 0) {
                fishNumber = 6
                fishCount += countFish(8,d+1,endDay)
            }
            else {
                fishNumber--
            }
        }

        return fishCount
    }

    /*
    * Optimized fish count where already counted values are stored in a map and reused later.
     */
    private fun countFishOptimized(timer : Int, endDay : Int) : Long {

        val key = timer + endDay

        if (!results.containsKey(key)) {
            results.put(key, countFish(timer, 1, endDay))
        }

        return results.get(key) ?: 0
    }
}