import kotlin.math.abs

class Day19 : AbstractDay(19) {

    private val scannermap : MutableMap<Int,List<Point3D>> = mutableMapOf()
    private val scannerPositions : MutableMap<Int,Pair<Int,Point3D>> = mutableMapOf(0 to (0 to Point3D(0,0,0)))

    override fun partOne(): Number {

        parseInput()
        calculateScannerPositions()
        return getBeaconSet().size
    }

    override fun partTwo(): Number {

        val positions = scannerPositions.values.map { (_,pos) -> pos }
        return cartesianProduct(positions,positions).maxOf {
            (p1,p2) -> abs(p1.x-p2.x) + abs(p1.y-p2.y) + abs(p1.z-p2.z)
        }

    }

    private fun calculateScannerPositions() {
        while(scannerPositions.size<scannermap.size) {
            scannermap.keys.filter { key -> !scannerPositions.containsKey(key) }.map { getScannerPosition(it) }
        }
    }

    private fun getBeaconSet() : Set<Point3D> {

        calculateScannerPositions()
        val beaconSet = mutableSetOf<Point3D>()

        scannermap.map { (k,points) ->
            val (rot,tran) = scannerPositions.get(k)!!
            points.map { pt ->
                beaconSet.add(pt.transform(rot).add(tran))
            }
        }

        return  beaconSet
    }
    

    private fun getRelativeScannerPosition(scanID1 : Int, scan1Orientation : Int, scanID2 : Int ) : List<Pair<Int,Point3D>> {

        val baseScanner = scannerPositions.get(scanID1)!!.second

        val result =  (0..23).map { orientation -> Pair(
            cartesianProduct(
                scannermap.get(scanID1)?.toList() ?: emptyList(),
                scannermap.get(scanID2)?.toList() ?: emptyList()
               ).map { Pair(it, it.first.transform(scan1Orientation).diff(it.second.transform(orientation))) }
                  .groupBy {  it.second.longID() }
                  .filterValues { vl -> vl.size == 12 }
            , orientation)
        }.filter { (m,_) -> m.size == 1 }
            .map {(hm, r) -> Pair(r, hm.values.first().first().second.add(baseScanner))}

        return result.take(1)

    }

    private fun getScannerPosition(scanID : Int)  {

        val relPosList = scannerPositions.map { (k,v) -> getRelativeScannerPosition(k, v.first, scanID) }.filter { it.size == 1 }
        if(relPosList.isNotEmpty()) {
            scannerPositions.put(scanID, relPosList.first().first())
        }
    }

    private fun parseInput() {

        var currentScanner = -1
        var currentBeaconList = mutableListOf<Point3D>()

        inputLines().forEach{
             if(it.isNotEmpty()) {
                 if(it.startsWith("---")) {
                     currentScanner = it.substring(12).dropLast(4).toInt()
                     currentBeaconList = mutableListOf()
                 }
                 else {
                     val coordinates = it.split(",").map { c -> c.toInt() }
                     currentBeaconList.add(Point3D(coordinates[0],coordinates[1],coordinates[2]))
                 }
             }
            else {
                scannermap.put(currentScanner,currentBeaconList)
             }
        }
        scannermap.put(currentScanner,currentBeaconList)
    }

}
