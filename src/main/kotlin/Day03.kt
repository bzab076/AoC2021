import kotlin.math.ceil

class Day03 : AbstractDay(3) {

    override fun partOne(): Number {

        val binSize = inputLines().first().length - 1
        val gamma = (0..binSize).fold(""){acc, i ->
            if(mostCommonBit(inputLines(),i) == '1') acc+"1"
            else acc+"0"
        }

        val epsilon = gamma.map { bit -> if(bit=='1') '0' else '1' }.joinToString("")

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    override fun partTwo(): Number {

        val binSize = inputLines().first().length - 1
        val oxy = (0..binSize).fold(inputLines()) {acc, i ->
            if(acc.size>1) acc.filter { v -> v.get(i) ==  mostCommonBit(acc,i)}
            else acc
        }.first().toInt(2)

        val co2 = (0..binSize).fold(inputLines()) {acc, i ->
            if(acc.size>1) acc.filter { v -> v.get(i) !=  mostCommonBit(acc,i)}
            else acc
        }.first().toInt(2)

        return oxy*co2
    }

    fun mostCommonBit(list : List<String>, index : Int) : Char {

        val ones = list.map{it -> it.get(index)}.filter { it -> it == '1' }.count()
        if(ones >= ceil(list.size.toDouble()/2))
            return '1'
        else
            return '0'
    }

}