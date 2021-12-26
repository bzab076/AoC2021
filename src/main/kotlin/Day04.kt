class Day04 : AbstractDay(4) {

    private var numbers : List<Int> = emptyList()
    private var boards : MutableList<MutableList<Pair<Int,Boolean>>> = emptyList<MutableList<Pair<Int,Boolean>>>().toMutableList()

    override fun partOne(): Number {

        parseInput()

        // play game
        for(num in numbers) {

            for(board in boards) {

                for(i in board.indices) {
                    if(board[i].first == num) {
                        board[i] = Pair(num,true)
                    }
                }

                if(isBoardComplete(board)) {
                    println("Winning board index ${boards.indexOf(board)}")
                    println("Winning number is ${num}")

                    val unmarked = board.filter { !it.second }.sumOf { it.first }
                    return unmarked*num
                }
            }

        }

        return 0 // should not happen
    }

    override fun partTwo(): Number {

        numbers = emptyList()
        boards  = emptyList<MutableList<Pair<Int,Boolean>>>().toMutableList()
        parseInput()

        val winningSet : MutableSet<Int> = emptySet<Int>().toMutableSet()

        // play game
        for(num in numbers) {

            for(board in boards) {

                for(i in board.indices) {
                    if(board[i].first == num) {
                        board[i] = Pair(num,true)
                    }
                }

                if(isBoardComplete(board)) {

                    winningSet.add(boards.indexOf(board))
                    if(winningSet.size == boards.size) {
                        println("Last winning board index " + boards.indexOf(board))
                        println("Last winning number is ${num}")

                        val unmarked = board.filter { !it.second }.sumOf { it.first }
                        return unmarked*num
                    }

                }
            }

        }

        return 0 // should not happen
    }

    private fun parseInput() {

        val rawInput = inputLines()
        numbers = rawInput.first().split(",").map{ it.toInt()}

        var currentBoard : MutableList<Pair<Int,Boolean>> = emptyList<Pair<Int,Boolean>>().toMutableList()

        for(line in rawInput.drop(2)) {

            if(line.trim().isEmpty()) {
                boards.add(currentBoard)
                currentBoard = emptyList<Pair<Int,Boolean>>().toMutableList()
                continue
            }

            val boardLine = line.split(" ").filter { it.isNotEmpty() }.map { Pair(it.toInt(), false) }
            for(elem in boardLine)
                currentBoard.add(elem)
        }

        boards.add(currentBoard)
    }

    private fun isBoardComplete(board : List<Pair<Int,Boolean>>) : Boolean {

        var lineComplete = false
        for(i in board.indices) {
            if(lineComplete && i % 5 == 0) break
            if(i % 5 == 0) lineComplete = board[i].second
            lineComplete = lineComplete && board[i].second
        }

        var columnComplete = false
        for (c in 0..4) {
            for(i in 0..4) {
                if(columnComplete && i == 0) break
                if(i == 0) columnComplete = board[c + i*5].second
                columnComplete = columnComplete && board[c + i*5].second
            }
        }

        return lineComplete || columnComplete
    }
}