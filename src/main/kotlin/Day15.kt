class Day15 : AbstractDay(15) {

    private val grid = inputDigits()
    private val maxY = grid.size
    private val maxX = grid[0].size
    private val startNode =  0 to 0

    override fun partOne(): Number {
        return findCheapestPath(mapOf(startNode to 0),1)
    }

    override fun partTwo(): Number {
        return findCheapestPath(mapOf(startNode to 0),5)
    }

    private tailrec fun findCheapestPath(costMap: Map<Point, Int>, multiplier : Int): Int {
        val w = maxX*multiplier
        val h = maxY*multiplier
        val destination = (h - 1) to (w - 1)

        val newCostMap = costMap
            .flatMap {
                    (point, cost) ->
                getNeighbors(x = point.first, y = point.second, w, h).map {
                        (x, y) -> (x to y) to getExtendedGridValue(x,y) + cost
                } + listOf(point to cost)
            }
            .groupBy { it.first }
            .mapValues { (_, costPairs) -> costPairs.minOf { cp -> cp.second } }
            .toMap()

        if(newCostMap == costMap) {
            return costMap.getValue(destination)
        }

        return findCheapestPath(newCostMap, multiplier)
    }

    private fun getNeighbors(x: Int, y: Int, maxx: Int, maxy: Int) = listOf(
        x - 1 to y, x to y - 1, x + 1 to y, x to y + 1)
        .filter { (x, y) -> x >= 0 && y >= 0 && x < maxx && y < maxy }


    private fun getExtendedGridValue(x: Int, y: Int): Int {
        val xx = x % maxX
        val yy = y % maxY
        val xOffset = x / maxX
        val yOffset = y / maxY

        var value = grid[yy][xx] + xOffset + yOffset
        if(value>9) value = value % 10 + 1

        return value
    }
}