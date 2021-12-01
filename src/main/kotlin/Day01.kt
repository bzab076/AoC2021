class Day01 : AbstractDay(1) {

    override fun partOne(): Number {

        var increased = 0
        for(i in inputLines().indices) {
            if(i==0) continue
            if(inputLines().get(i).toInt() > inputLines().get(i-1).toInt()) increased++
        }

        return increased
    }

    override fun partTwo(): Number {

        val s = inputLines().size

        var increased = 0
        for(i in inputLines().indices) {
            if(i==0) continue
            if(inputLines().get((i+2) % s).toInt() > inputLines().get(i-1).toInt()) increased++
        }

        return increased
    }
}