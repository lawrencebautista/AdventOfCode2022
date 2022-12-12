import kotlin.math.abs

class Day12 {
    fun execute() {
        val inputString = Utils.getInput("${this.javaClass.simpleName.lowercase()}.txt")
        val lines = inputString.split("\n").filterNot { it.isEmpty() }

        part1(lines)
        part2(lines)
    }

    private fun part1(lines: List<String>) {
        var start = Coord(0,0)
        var end = Coord(0,0)
        val matrix = Array(lines.size) {  x ->
            lines[x].mapIndexed { y, c ->
                if (c == 'S') start = Coord(x,y)
                if (c == 'E') end = Coord(x,y)
                c.height()
            }.toIntArray()
        }

        println(calculateMinimumDistance(matrix, start, end))
    }

    private fun part2(lines: List<String>) {
        // same idea but reverse the heights, and start from "E", finish on "a" or "S"
        var start = Coord(0,0)
        val endSet = mutableSetOf<Coord>()
        val matrix = Array(lines.size) {  x ->
            lines[x].mapIndexed { y, c ->
                if (c == 'S' || c == 'a') endSet.add(Coord(x,y))
                if (c == 'E') start = Coord(x,y)
                c.heightReversed()
            }.toIntArray()
        }
        println(calculateMinimumDistance(matrix, start, endSet))
    }

    private fun Char.height(): Int {
        return when (this) {
            'S' -> 'a'.height()
            'E' -> 'z'.height()
            else -> this - 'a'
        }
    }

    private fun Char.heightReversed(): Int {
        return when (this) {
            'S' -> 'a'.heightReversed()
            'E' -> 'z'.heightReversed()
            else -> abs(this - 'z')
        }
    }
    private fun calculateMinimumDistance(heights: Array<IntArray>, start: Coord, endSet: Set<Coord>): Int {
        val maxX = heights.size - 1
        val maxY = heights[0].size - 1

        val distances = Array(heights.size) {
            IntArray(heights[0].size) { -1 }
        }
        distances[start.x][start.y] = 0

        val queue = mutableListOf(start)

        var iteration = 0
        do {
            val current = queue.removeFirst()
            if (endSet.contains(current)) {
                return distances[current.x][current.y]
            }
            val x = current.x
            val y = current.y
            val currentHeight = heights[current.x][current.y]
            val currentDistance = distances[current.x][current.y]

            val steps = listOf(
                Coord(x-1,y),
                Coord(x+1,y),
                Coord(x,y-1),
                Coord(x,y+1)
            ).filter {
                it.x in 0..maxX && it.y in 0..maxY
                        && distances[it.x][it.y] == -1
                        && heights[it.x][it.y] <= currentHeight + 1
            }

            steps.forEach {
                distances[it.x][it.y] = currentDistance + 1
                queue.add(it)
            }
            iteration++
        } while (queue.isNotEmpty())

        return -1
    }

    private fun calculateMinimumDistance(heights: Array<IntArray>, start: Coord, end: Coord): Int {
        return calculateMinimumDistance(heights, start, setOf(end))
    }

    data class Coord(val x: Int, val y: Int) {
        override fun toString(): String {
            return "($x,$y)"
        }
    }
}