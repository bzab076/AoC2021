class Day09 : AbstractDay(9) {

    override fun partOne(): Number {

        val grid = getGrid()
        var result = 0

        for(r in grid.indices) {
            for(c in grid[r].indices) {

                val height = grid[r][c]
                val (up,down,left,right) = getAdjacentPoints(r, c, grid)

                if(height < up && height < down && height < left && height < right) {
                    result += (height + 1)
                }
            }
        }

        return result
    }

    override fun partTwo(): Number {

        val grid = getGrid()
        val basins : MutableList<Int> = emptyList<Int>().toMutableList()

        for(r in grid.indices) {
            for(c in grid[r].indices) {

                val height = grid[r][c]
                val (up,down,left,right) = getAdjacentPoints(r, c, grid)

                if(height < up && height < down && height < left && height < right){
                    basins.add( getBasinSize(r,c,grid,emptySet<Pair<Int,Int>>().toMutableSet()) )
                }

            }
        }

        return basins.sortedDescending().take(3).fold(1){acc, it -> acc*it}
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

    private fun getBasinSize(row : Int, col : Int, grid : Array<IntArray>, acc : MutableSet<Pair<Int,Int>>) : Int {

        acc.add(Pair(row,col))

        val (up,down,left,right) = getAdjacentPoints(row, col, grid)

        if(grid[row][col] < up && up<9 )  getBasinSize(row-1,col, grid, acc)
        if(grid[row][col] < down && down<9 )  getBasinSize(row+1,col, grid, acc)
        if(grid[row][col] < left && left<9 )  getBasinSize(row,col-1, grid, acc)
        if(grid[row][col] < right && right<9 )  getBasinSize(row,col+1, grid, acc)

        return acc.size
    }

    private fun getAdjacentPoints(row: Int,  col: Int, grid: Array<IntArray>): ArrayList<Int> {

        val up = if (row - 1 >= 0) grid[row - 1][col] else 10
        val down = if (row + 1 < grid.size) grid[row + 1][col] else 10
        val left = if (col - 1 >= 0) grid[row][col - 1] else 10
        val right = if (col + 1 < grid[row].size) grid[row][col + 1] else 10

        return arrayListOf<Int>(up,down,left,right)
    }
}