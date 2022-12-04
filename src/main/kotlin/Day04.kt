class Day04 {
    fun execute() {
        val inputString = Utils.getInput("day04.txt")
        val lines = inputString.split('\n').filterNot { it.isEmpty()}
        val pairs = lines.map { line ->
            val rangeStrings = line.split(',')

            val range1 = rangeStrings[0]
            val range2 = rangeStrings[1]

            val split1 = range1.split('-').map { it.toInt() }
            val split2 = range2.split('-').map { it.toInt() }
            Pair(
                AssignedRange(split1[0], split1[1]),
                AssignedRange(split2[0], split2[1])
            )
        }

        part01(pairs)
        part02(pairs)
    }

    private fun part01(pairs: List<Pair<AssignedRange, AssignedRange>>) {
        var count = 0
        pairs.forEach {
            if (it.first.low <= it.second.low && it.first.high >= it.second.high)
                count++
            else if (it.second.low <= it.first.low && it.second.high >= it.first.high)
                count++
        }
        println(count)
    }

    private fun part02(pairs: List<Pair<AssignedRange, AssignedRange>>) {
        var count = 0
        pairs.forEach {
            if (it.first.low.withinRange(it.second) ||
                it.first.high.withinRange(it.second) ||
                it.second.low.withinRange(it.first) ||
                it.second.high.withinRange(it.first)
            ) count++
        }
        println(count)
    }

    private fun Int.withinRange(range: AssignedRange): Boolean {
        return this >= range.low && this <= range.high
    }

    class AssignedRange(
        val low: Int,
        val high: Int
    )
}