class Day12 : AbstractDay(12) {

    private val caveMap : MutableList<Pair<String,String>> = parseInput()

    override fun partOne(): Number {
        return findPaths1("start", emptySet<String>().toMutableSet()).size
    }

    override fun partTwo(): Number {
        return findPaths2("start", emptySet<String>().toMutableSet(),"").size
    }

    /*
    * Returns list of pairs representing edges of the graph
     */
    private fun parseInput() : MutableList<Pair<String,String>> {

        val caveList = inputLines().map{it.split("-")}.map { Pair(it.first(),it.last()) }
        // because this is bidirectional graph we need pairs in opposite direction
        // but we discard pairs starting at end and ending at start
        val reverseNodes = caveList.map { (a,b) -> Pair(b,a) }.filter { (a,b) -> a!="end" && b!="start" }

        val map = emptyList<Pair<String,String>>().toMutableList()
        map.addAll(caveList)
        map.addAll(reverseNodes)
        return map
    }

    private fun findPaths1(startNode : String, visited : MutableSet<String>) : MutableSet<MutableList<String>> {

        if(startNode.equals("end")) return mutableSetOf(mutableListOf("end"))

        if(startNode.first().isLowerCase()) visited.add(startNode)
        val oldVisited = visited.toSet() // make visited set immutable to prevent recursive calls from corrupting it
        val pathsFromThisNode = emptySet<MutableList<String>>().toMutableSet()
        val nextNodes = caveMap.filter { it.first.equals(startNode) }.map{it.second}.filter { !visited.contains(it) }

        nextNodes.forEach { node ->
            val subpaths = findPaths1(node, oldVisited.toMutableSet())
            // add current node to each subpath
            subpaths.forEach { path ->
                path.add(startNode)
                pathsFromThisNode.add(path)
            }
        }

        return pathsFromThisNode
    }

    private fun findPaths2(startNode : String, visited : MutableSet<String>, visitedTwice : String) : MutableSet<MutableList<String>> {

        if(startNode.equals("end")) return mutableSetOf(mutableListOf("end"))
        var newVisitedTwice = visitedTwice

        if(startNode.first().isLowerCase()) {
            if(!visited.contains(startNode)){
                // first visit to this node
                visited.add(startNode)
            } else if(visited.contains(startNode) && visitedTwice.isEmpty()) {
                // second visit to this node
                newVisitedTwice = startNode
            } else if(visited.contains(startNode) && visitedTwice.isNotEmpty()) {
                // second visit to this node, but some other node has been visited twice - dead end, go back
                // should not happen
                return emptySet<MutableList<String>>().toMutableSet()
            }
        }
        val oldVisited = visited.toSet() // make visited set immutable to prevent recursive calls from corrupting it

        var forbiddenNodes = setOf("start")
        if(newVisitedTwice.isNotEmpty()) {
            forbiddenNodes = visited
        }

        val pathsFromThisNode = emptySet<MutableList<String>>().toMutableSet()
        val nextNodes = caveMap.filter { it.first.equals(startNode) }.map{it.second}.filter { !forbiddenNodes.contains(it) }

        nextNodes.forEach { node ->
            val subpaths = findPaths2(node, oldVisited.toMutableSet(), newVisitedTwice)
            // add current node to each subpath
            subpaths.forEach { path ->
                path.add(startNode)
                pathsFromThisNode.add(path)
            }
        }

        return pathsFromThisNode
    }
}