class Day13 : AbstractDay(13) {

    private var coordinates : List<Pair<Int,Int>> = emptyList()
    private var folds : List<Pair<Char,Int>> = emptyList()
    private var partOneResult  = 0

    override fun partOne(): Number {
        parseInput()
        foldPaper()
        return partOneResult
    }

    override fun partTwo(): Number {
        TODO("Not yet implemented")
    }

    private fun foldPaper()  {

        var oldFoldedCoordinates = coordinates.toMutableSet()
        var foldedCoordinates = emptySet<Pair<Int,Int>>().toMutableSet()

        var minX = 0
        var maxX = 0
        var minY = 0
        var maxY = 0

        var i = 0
        folds.forEach { (d,v) ->

            foldedCoordinates = emptySet<Pair<Int,Int>>().toMutableSet()

            oldFoldedCoordinates.forEach {
                if(d=='y' && it.second > v) {
                    foldedCoordinates.add(Pair(it.first, 2*v - it.second ))
                }
                else if (d=='x' && it.first > v) {
                    foldedCoordinates.add(Pair(2*v - it.first, it.second ))
                }
                else {
                    foldedCoordinates.add(it)
                }
            }

            minX = foldedCoordinates.map { (x, _) -> x }.minOf { it }
            maxX = foldedCoordinates.map { (x, _) -> x }.maxOf { it }
            minY = foldedCoordinates.map { (_,y) -> y }.minOf { it }
            maxY = foldedCoordinates.map { (_,y) -> y }.maxOf { it }

            if(++i==1) partOneResult = foldedCoordinates.size
            oldFoldedCoordinates = foldedCoordinates
        }

        // print part 2 result
        for(y in minY..maxY) {
            for(x in minX..maxX) {
                if(foldedCoordinates.contains(Pair(x,y))) {
                    print("#")
                }
                else {
                    print(" ")
                }
            }
            println()
        }

    }

    private fun parseInput() {

        val blankline = inputLines().indexOf("")
        coordinates = inputLines().take(blankline).map { it.split(",") }.map{Pair(it.first().toInt(), it.last().toInt())}
        folds = inputLines().takeLast(inputLines().size - blankline - 1).map { it.split("=") }.map{Pair(it.first().last(), it.last().toInt())}
    }
}