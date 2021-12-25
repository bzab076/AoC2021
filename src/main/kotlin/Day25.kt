class Day25 : AbstractDay(25) {

    private val rows = inputLines().size
    private val cols = inputLines().first().length
    private var map = Array(rows) { IntArray(cols) }

    override fun partOne(): Number {
        parseInput()
        return simulateMovements()
    }

    override fun partTwo(): Number {
        TODO("Not yet implemented")
    }

    private fun simulateMovements() : Int {

        var step = 0
        var oldMap = map
        var hasMoved = true
        while(hasMoved) {

            var newMap = Array(rows) { IntArray(cols) }
            hasMoved = false
            // move east
            for(r in 0 until rows)
                for(c in 0 until cols) {
                    if(oldMap[r][c] == 1 && oldMap[r][(c+1) % cols] == 0){
                        newMap[r][(c+1) % cols] = 1
                        hasMoved = true
                    }
                    else if(oldMap[r][c] == 1) newMap[r][c] = 1
                    else if(oldMap[r][c] == 2) newMap[r][c] = 2
                }

            oldMap = newMap
            newMap = Array(rows) { IntArray(cols) }
            // move south
            for(r in 0 until rows)
                for(c in 0 until cols) {
                    if(oldMap[r][c] == 2 && oldMap[(r+1) % rows][c] == 0){
                        newMap[(r+1) % rows][c] = 2
                        hasMoved = true
                    }
                    else if(oldMap[r][c] ==  2) newMap[r][c] = 2
                    else if(oldMap[r][c] ==  1) newMap[r][c] = 1
                }

            step++
            oldMap = newMap
        }

        return step
    }

    private fun parseInput() {

        var r = 0
        inputLines().forEach { line ->
            for(c in line.indices)
                map[r][c] = when(line[c]) {
                    '>' -> 1
                    'v' -> 2
                    '.' -> 0
                    else -> throw Exception("Invalid input!")
                }
            r++
        }

    }
}