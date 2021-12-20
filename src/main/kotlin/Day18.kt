class Day18 : AbstractDay(18) {

    override fun partOne(): Number {
        var resultSnailFish = inputLines().first()
        inputLines().drop(1).forEach { resultSnailFish = addSnailFish(resultSnailFish,it) }
        return parseSnailFish(resultSnailFish).magnitude()
    }

    override fun partTwo(): Number {
        return cartesianProduct(inputLines(),inputLines())
            .filter { (a,b) -> a!=b }
            .maxOf { (a,b) -> parseSnailFish(addSnailFish(a,b)).magnitude() }
    }
}

fun parseSnailFish(element : String) : SnailFishNumber {

    val content = element.substring(1,element.length-1)
    val el1: String
    val el2: String
    if(content[0].isDigit()) {
        el1 = content[0].toString()
        el2 = content.substring(2)
    }
    else {
        var parCount = 0
        var i = 0
        do {
            if(content[i]=='[') parCount++
            if(content[i]==']') parCount--
            i++
        } while(parCount>0)

        el1 = content.substring(0,i)
        el2 = content.substring(i+1)
    }
    return SnailFishNumber(el1,el2)
}

fun explode(snailFish : String) : Pair<String,Boolean> {

    var bracketLevel = 0
    var leftDigit : Pair<Int,Int> = -1 to -1
    var rightDigit : Pair<Int,Int> = -1 to -1
    var leftExplode : Pair<Int,Int> = -1 to -1
    var rightExplode : Pair<Int,Int> = -1 to -1
    var inAngleBracket = false
    var doubleDigitStr = ""
    var doubleDigitIdx = -1
    for(i in snailFish.indices) {
        if (snailFish[i] == '[') bracketLevel++
        if (snailFish[i] == ']') bracketLevel--
        if (snailFish[i] == '<') {
            inAngleBracket = true
            doubleDigitIdx = i
        }
        if (snailFish[i] == '>') {
            inAngleBracket = false
            val doubleDigitNum = doubleDigitStr.toInt()
            doubleDigitStr = ""
            if(bracketLevel>4) {
                if(leftExplode.second==-1) leftExplode = doubleDigitNum to doubleDigitIdx
                else if(rightExplode.second == -1) rightExplode = doubleDigitNum to doubleDigitIdx
            }
            if(leftExplode.second==-1 && rightExplode.second == -1) leftDigit = doubleDigitNum to doubleDigitIdx
            if(leftExplode.second>0 && rightExplode.second>0 && rightExplode.second<doubleDigitIdx && rightDigit.second==-1) rightDigit = doubleDigitNum to doubleDigitIdx
        }
        if(snailFish[i].isDigit() && inAngleBracket) doubleDigitStr+=snailFish[i]
        if (snailFish[i].isDigit() && !inAngleBracket) {
            if(bracketLevel>4) {
                if(leftExplode.second==-1) leftExplode = snailFish[i].digitToInt() to i
                else if(rightExplode.second == -1) rightExplode = snailFish[i].digitToInt() to i
            }
            if(leftExplode.second==-1 && rightExplode.second == -1) leftDigit = snailFish[i].digitToInt() to i
            if(leftExplode.second>0 && rightExplode.second>0 && rightExplode.second<i && rightDigit.second==-1) rightDigit = snailFish[i].digitToInt() to i
        }

    }

    if(leftExplode.second > 0 && rightExplode.second > 0) {
        // there are numbers to explode
        var explodedString: String
        if(leftDigit.second>0) {
            explodedString = snailFish.substring(0,leftDigit.second) +
                    splitNumber(leftDigit.first+leftExplode.first) +
                    snailFish.substring(nextIndex(leftDigit),leftExplode.second-1)
        }
        else {
            explodedString = snailFish.substring(0,leftExplode.second-1)
        }
        explodedString += "0"
        if(rightDigit.second>0) {
            explodedString += snailFish.substring(nextIndex(rightExplode)+1,rightDigit.second)
            explodedString +=  splitNumber(rightExplode.first+rightDigit.first)
            explodedString += snailFish.substring(nextIndex(rightDigit))
        }
        else {
            explodedString += snailFish.substring(nextIndex(rightExplode)+1)
        }

        return explodedString to true
    }
    else {
        return snailFish to false
    }
}

private fun nextIndex(digitPair : Pair<Int,Int>) : Int = if(digitPair.first<10) digitPair.second + 1 else digitPair.second + digitPair.first.toString().length  + 2


fun reduce (snailFish : String) : String {

    var current = snailFish

    while(true) {
        val (explodedStr, hasExploded) = explode(current)
        if (hasExploded) {
            current = explodedStr
            continue
        }

        val (splitStr, hasSplit) = split(current)
        current = splitStr
        if (!hasSplit) {
            break
        }
    }

    return current
}

fun splitNumber(number: Int): String = if(number<10) number.toString() else "<$number>"


fun split(snailFish : String) : Pair<String,Boolean> {

    if(!snailFish.contains('<')) return snailFish to false

    val startIdx = snailFish.indexOf('<')
    val endIdx = snailFish.indexOf('>')
    val number = snailFish.substring(startIdx+1,endIdx).toInt()
    val part1 = number / 2
    val part2 = part1 + (number % 2)
    val splitNumber = "[${splitNumber(part1)},${splitNumber(part2)}]"
    return snailFish.substring(0,startIdx) + splitNumber + snailFish.substring(endIdx+1) to true
}

private fun addSnailFish(first : String, second :String) : String = reduce("[${first},${second}]")

class SnailFishNumber(val first: String, val second:String) {

    fun magnitude() : Int {
        val v1 = if(first.length==1 && first[0].isDigit()) first.toInt() else parseSnailFish(first).magnitude()
        val v2 = if(second.length==1 && second[0].isDigit()) second.toInt() else parseSnailFish(second).magnitude()
        return 3*v1 + 2*v2
    }
}