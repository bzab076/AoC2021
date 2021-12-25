class Day23 : AbstractDay(23) {

    private val hallNodes : List<Node> = listOf(
        Node("H0", 0, false, true),
        Node("H1", 1, false, true),
        Node("H2", 2, false, false),
        Node("H3", 3, false, true),
        Node("H4", 4, false, false),
        Node("H5", 5, false, true),
        Node("H6", 6, false, false),
        Node("H7", 7, false, true),
        Node("H8", 8, false, false),
        Node("H9", 9, false, true),
        Node("H10", 10, false, true)
    )

    private val aRoomNodes : List<Node> = listOf(
        Node("A1", 1, true, true),
        Node("A2", 2, true, true),
        Node("A3", 3, true, true),
        Node("A4", 4, true, true)
    )

    private val bRoomNodes : List<Node> = listOf(
        Node("B1", 1, true, true),
        Node("B2", 2, true, true),
        Node("B3", 3, true, true),
        Node("B4", 4, true, true)
    )

    private val cRoomNodes : List<Node> = listOf(
        Node("C1", 1, true, true),
        Node("C2", 2, true, true),
        Node("C3", 3, true, true),
        Node("C4", 4, true, true)
    )

    private val dRoomNodes : List<Node> = listOf(
        Node("D1", 1, true, true),
        Node("D2", 2, true, true),
        Node("D3", 3, true, true),
        Node("D4", 4, true, true)
    )

    private val amphipods : List<Amphipod> = listOf(
        Amphipod("A1",'A'),
        Amphipod("A2",'A'),
        Amphipod("A3",'A'),
        Amphipod("A4",'A'),
        Amphipod("B1",'B'),
        Amphipod("B2",'B'),
        Amphipod("B3",'B'),
        Amphipod("B4",'B'),
        Amphipod("C1",'C'),
        Amphipod("C2",'C'),
        Amphipod("C3",'C'),
        Amphipod("C4",'C'),
        Amphipod("D1",'D'),
        Amphipod("D2",'D'),
        Amphipod("D3",'D'),
        Amphipod("D4",'D')
    )

    private val initialState : Map<Node, Amphipod> = mapOf(
        Node("A1", 1, true, true) to Amphipod("B1",'B'),
        Node("A2", 2, true, true) to Amphipod("D1",'D'),
        Node("A3", 3, true, true) to Amphipod("D2",'D'),
        Node("A4", 4, true, true) to Amphipod("C1",'C'),
        Node("B1", 1, true, true) to Amphipod("B2",'B'),
        Node("B2", 2, true, true) to Amphipod("C2",'C'),
        Node("B3", 3, true, true) to Amphipod("B3",'B'),
        Node("B4", 4, true, true) to Amphipod("A1",'A'),
        Node("C1", 1, true, true) to Amphipod("D3",'D'),
        Node("C2", 2, true, true) to Amphipod("B4",'B'),
        Node("C3", 3, true, true) to Amphipod("A2",'A'),
        Node("C4", 4, true, true) to Amphipod("A3",'A'),
        Node("D1", 1, true, true) to Amphipod("D4",'D'),
        Node("D2", 2, true, true) to Amphipod("A4",'A'),
        Node("D3", 3, true, true) to Amphipod("C3",'C'),
        Node("D4", 4, true, true) to Amphipod("C4",'C')
    )

    override fun partOne(): Number {
        // solved by hand
        return 10607
    }

    override fun partTwo(): Number {
        return findCheapestSolution(initialState, 0)
    }

    private fun findCheapestSolution(state: Map<Node, Amphipod>, accumulatedCost : Int) : Int {

        if(isEndState(state)) return accumulatedCost

        val allPaths = state.filter { (_,v) -> !v.inFinalDestination }
            .map { (k,v) -> findPathsToDestinations(k,v,state)}.flatten()

        // this is dead end
        if(allPaths.isEmpty()) return 0

        val costs =  allPaths.map { path ->
           val amp : Amphipod = state.get(path.first())!!
            findCheapestSolution(
                moveAmphipod(path.first(),path.last(),amp,state),
            accumulatedCost + (path.size - 1)*getEnergyConsumption(amp.type)
            )
        }

        return if(costs.filter { it > 0 }.minOrNull() != null) costs.filter { it > 0 }.minOrNull()!! else 0
    }

    private fun moveAmphipod (start: Node, destination: Node, amp : Amphipod, state: Map<Node, Amphipod>) :  Map<Node, Amphipod> {

        //println (" move ${amp.name} from ${start.name}  to ${destination.name}")
        val newState = HashMap(state).toMutableMap()
        val newAmp = Amphipod(amp.name,amp.type)
        newAmp.stoppedInHall = amp.stoppedInHall
        newState.remove(start, amp)
        if(!destination.isRoom) newAmp.stoppedInHall = true
        else newAmp.inFinalDestination = true
        newState.put(destination,newAmp)
        return  newState.toMap()
    }

    private fun isEndState (state : Map<Node, Amphipod>) : Boolean  =
                state.size == 16 &&
                state.keys.all { it.isRoom } &&
                state.all { (k,v) -> k.name.startsWith(v.type) }

    private fun findPath(start : Node, destination : Node, state : Map<Node, Amphipod>) : List<Node> {

        if((start.isRoom && destination.isRoom) || (!start.isRoom && !destination.isRoom)) return emptyList()
        return if(start.isRoom) findPathToHall(start, destination, state) else findPathToRoom(start, destination, state)
    }

    private fun findPathToRoom(start: Node, destination: Node, state: Map<Node, Amphipod>): List<Node> {

        val path = mutableListOf<Node>()
        val hallIdx = getHallIndex(destination)
        val increment = if(start.index < hallIdx) 1 else -1
        var idx = start.index
        while(idx!=hallIdx) {
            val hallNode = hallNodes.find { it.index == idx }!!
            if(idx!=start.index && hallNode.canStay && state.containsKey(hallNode)) {
                // the path is occupied
                return emptyList()
            }
            path.add(hallNode)
            idx += increment
        }
        val hallNode = hallNodes.find { it.index == hallIdx }!!
        path.add(hallNode)

        val roomNodes = getRoomNodes(destination.name[0])
        idx = 1
        while(idx<=destination.index) {
            val roomNode = roomNodes.find { it.index == idx }!!
            if(state.containsKey(roomNode)) {
                // the path is occupied
                return emptyList()
            }
            path.add(roomNode)
            idx++
        }
        return path.toList()
    }

    private fun findPathToHall(start: Node, destination: Node, state: Map<Node, Amphipod>): List<Node> {

        val path = mutableListOf<Node>()
        val roomNodes = getRoomNodes(start.name[0])
        var idx = start.index
        while(idx>0) {
            val roomNode = roomNodes.find { it.index == idx }!!
            if(idx!=start.index && state.containsKey(roomNode)) {
                // the path is occupied
                return emptyList()
            }
            path.add(roomNode)
            idx--
        }

        var hallIdx = getHallIndex(start)
        val increment = if(hallIdx < destination.index) 1 else -1
        while(hallIdx != destination.index) {
            val hallNode = hallNodes.find { it.index == hallIdx }!!
            if(hallNode.canStay && state.containsKey(hallNode)) {
                // the path is occupied
                return emptyList()
            }
            path.add(hallNode)
            hallIdx += increment
        }
        path.add(destination)
        return path.toList()
    }

    private fun findPotentialDestinations(amp: Amphipod, state: Map<Node, Amphipod>) : List<Node> {

        if(amp.inFinalDestination) return emptyList()
        if(amp.stoppedInHall) return if(findDestinationNode(amp,state)!=null) listOf(findDestinationNode(amp,state)) as List<Node> else emptyList()

        val destinations = hallNodes.filter { node -> node.canStay && !state.keys.contains(node) }.toMutableList()
        if(findDestinationNode(amp,state)!=null) destinations.add(findDestinationNode(amp,state)!!)
        return destinations.toList()
    }

    private fun findPathsToDestinations(startNode : Node, amp: Amphipod, state: Map<Node, Amphipod>) : List<List<Node>> =
        findPotentialDestinations(amp, state).map{findPath(startNode, it, state)}.filter { it.isNotEmpty() }

    private fun findDestinationNode(amp: Amphipod, state: Map<Node, Amphipod>) : Node? {
        if(amp.inFinalDestination) return null
        if(roomContainsOther(amp.type, state)) return null
        val roomNodes = getRoomNodes(amp.type)
        return roomNodes.filter { node -> !state.keys.contains(node) }.maxByOrNull { it.index }!!
    }

    private fun roomContainsOther(roomID : Char, state: Map<Node, Amphipod>) : Boolean =
        state.filter { (k,v) -> k.isRoom && k.name.startsWith(roomID) && v.type!=roomID }.isNotEmpty()

    private fun getRoomNodes( roomID: Char) : List<Node>  =
         when (roomID) {
            'A' -> aRoomNodes
            'B' -> bRoomNodes
            'C' -> cRoomNodes
            'D' -> dRoomNodes
            else -> throw Exception("Invalid room node")
        }

    private fun getHallIndex(roomNode: Node) : Int  =
        when (roomNode.name[0]) {
            'A' -> 2
            'B' -> 4
            'C' -> 6
            'D' -> 8
            else -> throw Exception("Invalid room node")
        }

    private fun getEnergyConsumption(ampType : Char) : Int  =
        when (ampType) {
            'A' -> 1
            'B' -> 10
            'C' -> 100
            'D' -> 1000
            else -> throw Exception("Invalid amphipod type")
        }

}

data class Node(val name : String, val index : Int, val isRoom : Boolean, val canStay : Boolean)

data class Amphipod(val name : String, val type : Char) {
    var stoppedInHall : Boolean = false
    var inFinalDestination : Boolean = false
}