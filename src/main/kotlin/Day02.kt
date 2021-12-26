class Day02 : AbstractDay(2) {

    override fun partOne(): Number {

        val input = inputLines().map { it.split(" ") }

        val horizontalPos = input.filter { (dir, _) -> dir.equals("forward") }.sumOf { (_, value) -> value.toInt() }
        val down = input.filter { (dir, _) -> dir.equals("down") }.sumOf { (_, value) -> value.toInt() }
        val up = input.filter { (dir, _) -> dir.equals("up") }.sumOf { (_, value) -> value.toInt() }

        return horizontalPos * (down - up)
    }

    override fun partTwo(): Number {

        val input = inputLines().map { it.split(" ") }

        var aim = 0
        var horizontalPos = 0
        var depth = 0

        for((dir,value) in input) {

            if(dir.equals("forward")) {
                horizontalPos += value.toInt()
                depth += aim*value.toInt()
            }

            if(dir.equals("down")) {
                aim += value.toInt()
            }

            if(dir.equals("up")) {
                aim -= value.toInt()
            }

        }

        return  horizontalPos*depth

    }

    /*
     * Alternative solution for part two in functional programming style, specifically using tail recursion.
     * Accumulator is a triple consisting of (horizontal position, depth, aim)
     * To get the result call secondPuzzle(input, Triple(0,0,0))
     */
    fun secondPuzzle(inputData : List<List<String>>, acc : Triple<Int, Int, Int>) : Number {

        if(inputData.isEmpty())
            return acc.first * acc.second

        val elem = inputData.first()
        val value = elem.last().toInt()
        return when (elem.first()) {
            "forward" -> secondPuzzle(inputData.drop(1), Triple(acc.first + value,acc.second + acc.third*value,acc.third))
            "down" -> secondPuzzle(inputData.drop(1), Triple(acc.first,acc.second,acc.third + value))
            "up" -> secondPuzzle(inputData.drop(1), Triple(acc.first,acc.second,acc.third - value))
            else -> -1 // should never happen
        }

    }
}