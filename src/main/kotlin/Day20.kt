class Day20 : AbstractDay(20) {

    private val enhancementAlgorithm = inputLines().first()
    private val image0 = getImage()

    override fun partOne(): Number {

        val image1 = enhanceImage(image0, false)
        val image2 = enhanceImage(image1, true)
        return image2.size
    }

    override fun partTwo(): Number {

        var oddImage = enhanceImage(image0, false)
        var evenImage = emptySet<Point>()
        for(i in 2..50) {
            if(i % 2 == 0) evenImage = enhanceImage(oddImage, true)
            else oddImage = enhanceImage(evenImage, false)
        }
        return evenImage.size

    }

    private fun getImage() : Set<Point> {

        val pixelSet = mutableSetOf<Point>()
        var y = 0
        inputLines().drop(2).forEach { row ->
            for(x in row.indices)
                if(row.get(x)=='#') pixelSet.add(x to y)
            y++
        }

        return pixelSet
    }

    private fun enhanceImage(image : Set<Point>, pixelOn : Boolean) : Set<Point> {

        val newImage = mutableSetOf<Point>()

        val minx = image.toList().minOf { (x,_) -> x }
        val maxx = image.toList().maxOf { (x,_) -> x }
        val miny = image.toList().minOf { (_,y) -> y }
        val maxy = image.toList().maxOf { (_,y) -> y }

        val neighbors = listOf(-1 to -1, 0 to -1, 1 to -1, -1 to 0, 0 to 0, 1 to 0, -1 to 1, 0 to 1, 1 to 1)

        for(y in miny-1..maxy+1)
            for(x in minx-1..maxx+1) {
                val binString = neighbors.map { (x1,y1) ->
                         if((image.contains(x+x1 to y+y1) && !pixelOn) || (!image.contains(x+x1 to y+y1) && pixelOn)) '1' else '0'
                    }.joinToString("")
                val index = binString.toInt(2)
                if((enhancementAlgorithm.get(index) == '#' && pixelOn) || (enhancementAlgorithm.get(index) == '.' && !pixelOn) ) newImage.add(x to y)

            }

        return newImage
    }
}