class Day11 : AbstractDay(11) {

    private var totalFlashes : Int = 0  // for part 1
    private var synchronizedStep : Int = 0 // for part 2

    private fun simulateOctopusFlashing(partOneSteps : Int, debug : Boolean) {

        val grid = getGrid()
        var allFlashes = 0
        var step = 1
        var flashesInStep = 0

        while(flashesInStep<100) {

            flashesInStep = 0

            for(r in grid.indices) {
                for(c in grid[r].indices) {
                    grid[r][c]++
                }
            }

            var energyIncreased: Boolean
            do{
                energyIncreased = false
                for(r in grid.indices) {
                    for(c in grid[r].indices) {
                        if(grid[r][c] >= 10) {  // octopus flashes

                            for(i in -1..1) {
                                for(j in -1..1) {
                                    if(i!=0 || j!=0) {
                                        if(r+i >=0 && r+i < grid.size && c+j>=0 && c+j<grid[r].size) {
                                            if(grid[r+i][c+j] >0) {
                                                grid[r+i][c+j]++
                                                energyIncreased = true
                                            }

                                        }
                                    }
                                }
                            }

                            grid[r][c] = 0 // set flashed octopus to 0, so we don't visit it anymore
                            allFlashes++
                            flashesInStep++
                        }
                    }
                }

            } while (energyIncreased)

            if (debug) {
                println("step ${step}:  flashes ${flashesInStep}")
                for(r in grid.indices) {
                    println(grid[r].toList().joinToString(""))
                }
            }

            if(step == partOneSteps) {
                // remember allFlashes after 100 steps for part 1 solution
                totalFlashes = allFlashes
            }

            if(flashesInStep == 100) {
                // all octopuses are synschronized
                synchronizedStep = step
            }

            step++
        }

    }

    override fun partOne(): Number {

        simulateOctopusFlashing(100, false)
        return totalFlashes
    }

    override fun partTwo(): Number {

        return synchronizedStep
    }

    private fun getGrid(): Array<IntArray> {

        val rows = inputLines().size
        val cols = inputLines().first().length
        val grid = Array(rows) { IntArray(cols) }

        var r = 0
        inputLines().forEach {
            for(c in it.indices) {
                grid[r][c] = it.get(c).toString().toInt()
            }
            r++
        }

        return grid
    }
}