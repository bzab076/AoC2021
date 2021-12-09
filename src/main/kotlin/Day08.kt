class Day08 : AbstractDay(8) {

    override fun partOne(): Number {

        val outputNums = inputLines().map { it -> it.substringAfter(" | ").split(" ") }
        return outputNums.map { it -> it.count{ s -> listOf(2,3,4,7).contains(s.length) } }.sum()
    }

    override fun partTwo(): Number {

        return inputLines().sumOf { getNumber(it) }
    }

    /*
    * Finds a digit as char in the map. Key digits (pattern) may permute.
     */
    private fun findDigit(pattern : String, dmap : Map<String,Int>) : Char {

        val key = dmap.keys.find { matchByChar(pattern, it)}
        return  dmap.get(key).toString().first()
    }

    /*
    * Based on signal patterns, creates a map which maps pattern to a digit.
     */
    private fun getWireMap(input : List<String>) : Map<String,Int> {

        val result : MutableMap<String,Int> = emptyMap<String,Int>().toMutableMap()

        // pattern of length 2 always maps to 1
        val one = input.find { it.length==2 } as String
        result.put(one, 1)

        // pattern of length 3 always maps to 7
        val seven = input.find { it.length==3 } as String
        result.put(seven,7)

        // pattern of length 4 always maps to 4
        val four = input.find { it.length==4 } as String
        result.put(four,4)

        // pattern of length 7 always maps to 8
        val eight = input.find { it.length==7 } as String
        result.put(eight,8)

        // determine patterns of length 5
        val fives = input.filter { it.length==5 }
        fives.forEach {
            if(it.contains(one[0]) && it.contains(one[1])) {
                result.put(it,3)
            }
            else if (commonCharacters(it, four) == 3) {
                result.put(it,5)
            }
            else
                result.put(it,2)
        }

        // determine patterns of length 6
        val sixes = input.filter { it.length==6 }
        sixes.forEach {
            if(!it.contains(one[0]) || !it.contains(one[1])) {
                result.put(it,6)
            }
            else if (it.contains(four[0]) && it.contains(four[1]) && it.contains(four[2]) && it.contains(four[3])) {
                result.put(it,9)
            }
            else
                result.put(it,0)
        }

        return result
    }

    /*
    * Determines decimal number that corresponds to a line of puzzle input
     */
    private fun getNumber(line : String) : Int {

        val signal = line.substringBefore(" | ").split(" ")
        val output = line.substringAfter(" | ").split(" ")
        val wireMap = getWireMap(signal)
        val res = output.map{ findDigit(it,wireMap)}.joinToString("")

        return res.toInt()
    }

    /*
    * Returns number of characters two strings have in common
     */
    private fun commonCharacters (s1 : String, s2 : String) : Int  = s1.toSet().intersect(s2.toSet()).size


    /*
     *  Finds whether two patterns have same characters (may be in different order)
    */
    private fun matchByChar(pat1 : String, pat2 : String) = (pat1.length == pat2.length) && (commonCharacters(pat1,pat2) == pat1.length)

}