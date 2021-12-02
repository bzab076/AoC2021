class Day02 : AbstractDay(2) {

    override fun partOne(): Number {

        val input = inputLines().map { it -> it.split(" ") }

        val horizontalPos = input.filter { (dir,_) -> dir.equals("forward") }.map{ (_,value) -> value.toInt()}.sum()
        val down = input.filter { (dir,_) -> dir.equals("down") }.map{(_,value) -> value.toInt()}.sum()
        val up = input.filter { (dir,_) -> dir.equals("up") }.map{(_,value) -> value.toInt()}.sum()

        val result = horizontalPos * (down - up)

        return result

    }

    override fun partTwo(): Number {

        val input = inputLines().map { it -> it.split(" ") }

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
}