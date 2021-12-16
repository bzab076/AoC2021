class Day16 : AbstractDay(16) {

    override fun partOne(): Number {
        val binInput = hex2bin(inputString())
        val (p,_) = parsePacket(binInput)
        return p.versionSum
    }

    override fun partTwo(): Number {
        val binInput = hex2bin(inputString())
        val (p,_) = parsePacket(binInput)
        return p.evaluate()
    }

    private fun hex2bin (input : String) =
        input.toCharArray().toList().map { ch ->
            ch.toString().toInt(16).toString(2).padStart(4,'0')
        }.fold(""){acc, it -> acc+it}

    private fun parsePacket(binInput : String) : Pair<Packet,String> {

        if(binInput.isEmpty()) return Packet(0,0) to ""

        val packetVersion = binInput.substring(0,3).toInt(2)
        val packetTypeID = binInput.substring(3,6).toInt(2)
        val subpackets = emptyList<Packet>().toMutableList()
        var value = ""
        var remainder: String

        if(packetTypeID==4) {
            var grpIdx  = 6
            var group = binInput.substring(grpIdx,grpIdx+5)
            while (group[0]=='1') {
                value += group.substring(1)
                grpIdx += 5
                group = binInput.substring(grpIdx,grpIdx+5)
            }
            value += group.substring(1)
            grpIdx += 5
            remainder = binInput.substring(grpIdx)
        }
        else {
            // operator
            val lengthTypeID = binInput[6].digitToInt()
            val startIndex: Int
            if(lengthTypeID==0) {
                val subPacketLength = binInput.substring(7,22).toInt(2)
                startIndex = 22
                val pl = parsePackets(binInput.substring(startIndex, startIndex+subPacketLength))
                subpackets.addAll(pl)
                remainder = binInput.substring(startIndex+subPacketLength)
            }
            else {
                val numSubPackets = binInput.substring(7,18).toInt(2)
                startIndex = 18
                remainder = binInput.substring(startIndex)
                repeat(numSubPackets) {
                    val (p,r) = parsePacket(remainder)
                    subpackets.add(p)
                    remainder = r
                }
            }
        }
        return Packet(packetVersion,packetTypeID,if(value.isEmpty()) 0 else value.toLong(2), subpackets) to remainder
    }

    private fun parsePackets(binInput: String) : List<Packet> {

        var remainder = binInput
        val packetList = emptyList<Packet>().toMutableList()
        while (remainder.length>10) {
            val (p,r) = parsePacket(remainder)
            packetList.add(p)
            remainder = r
        }

        return packetList
    }

    data class Packet(val version: Int, val type: Int, val value: Long? = null, val subPackets: MutableList<Packet> = mutableListOf()) {
        val versionSum: Int
            get() = version + subPackets.sumOf { it.versionSum }

        fun evaluate() : Long {

            return when(type) {
                0 -> subPackets.sumOf { it.evaluate() }
                1 -> subPackets.fold(1L){ acc, it -> acc*it.evaluate()}
                2 -> subPackets.minOf { it.evaluate() }
                3 -> subPackets.maxOf { it.evaluate() }
                4 -> value!!
                5 -> if(subPackets[0].evaluate() > subPackets[1].evaluate()) 1L else 0L
                6 -> if(subPackets[0].evaluate() < subPackets[1].evaluate()) 1L else 0L
                7 -> if(subPackets[0].evaluate() == subPackets[1].evaluate()) 1L else 0L
                else -> -1L
            }
        }
 
    }

}