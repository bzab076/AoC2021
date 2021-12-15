class Day05 : AbstractDay(5) {

    val ventCoordinates : MutableList<Pair<Pair<Int,Int>,Pair<Int,Int>>> = emptyList<Pair<Pair<Int,Int>,Pair<Int,Int>>>().toMutableList()

    private val rows = 1000
    private val cols = 1000
    private var grid = Array(rows) { IntArray(cols) }

    override fun partOne(): Number {

        parseInput()
        setVents(false)

        return grid.toList().map { it -> it.toList().filter { x -> x>1 }.count() }.sum()
    }

    override fun partTwo(): Number {

        initializeGrid()
        setVents(true)

        return grid.toList().map { it -> it.toList().filter { x -> x>1 }.count() }.sum()
    }

    private fun parseInput() {

        inputLines().forEach {
            val line = it.split(" -> ")
            val p1 = line.first().split(",")
            val p2 = line.last().split(",")
            ventCoordinates.add(Pair(Pair(p1.first().toInt(),p1.last().toInt()), Pair(p2.first().toInt(),p2.last().toInt())))
        }

    }

    private fun initializeGrid() {

        for(i in 0 until grid.size) {
            for(j in 0 until grid[i].size) {
                grid[i][j] = 0
            }
        }
    }

    private fun setVents(diagonal : Boolean) {

        ventCoordinates.forEach {

            val isLineValid =
                it.first.first == it.second.first ||  //vertical
                it.first.second == it.second.second ||  // horizontal
                diagonal &&  (kotlin.math.abs(it.first.first - it.second.first) == kotlin.math.abs(it.first.second - it.second.second)) // diagonal

            if (isLineValid) {

                val xIncrement : Int
                val yIncrement : Int

                if(it.first.first == it.second.first) {
                    xIncrement = 0
                }
                else if(it.first.first < it.second.first) {
                    xIncrement = 1
                } else {
                    xIncrement = -1
                }

                if(it.first.second == it.second.second) {
                    yIncrement = 0
                }
                else if(it.first.second < it.second.second) {
                    yIncrement = 1
                } else {
                    yIncrement = -1
                }

                var x = it.first.first - xIncrement
                var y = it.first.second - yIncrement

                do{
                    x+=xIncrement
                    y+=yIncrement
                    grid[x][y]++
                } while((x != it.second.first) || (y != it.second.second))
            }

        }

    }

}