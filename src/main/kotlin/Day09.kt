import java.lang.Integer.max
import kotlin.math.abs

class Day09 {

    class Motion(
        val direction: Direction,
        val spaces: Int
    )

    enum class Direction {
        L,
        R,
        U,
        D
    }
    fun execute() {
        val inputString = Utils.getInput("day09.txt")
        val lines = inputString.split("\n").filterNot { it.isEmpty() }

        val motions = lines.map {
            val tokens = it.split(' ')
            Motion(
                Direction.valueOf(tokens[0]),
                tokens[1].toInt()
            )
        }

//        part1(motions)
        part2(motions)
    }
    private fun part1(motions: List<Motion>) {
        val singleMotions = motions.flatMap {  motion ->
            List(motion.spaces) {
                motion.direction
            }
        }

        var (hX, hY, tX, tY) = listOf(0, 0, 0, 0)
        val tailVisited = mutableSetOf<Pair<Int, Int>>()

        singleMotions.forEach {
            when (it) {
                Direction.U -> hY += 1
                Direction.D -> hY -= 1
                Direction.R -> hX += 1
                Direction.L -> hX -= 1
            }

            val newTail = tailCoordsAfterHeadMoved1(hX, hY, tX, tY)
            tailVisited.add(newTail)
            tX = newTail.first
            tY = newTail.second
        }

        println(tailVisited.count())
    }

    private fun tailCoordsAfterHeadMoved1(hX: Int, hY: Int, tX: Int, tY: Int): Pair<Int, Int> {
        return when {
            hX - tX > 1 -> {
                if (hY == tY) Pair(tX + 1, tY)
                else if (hY > tY) Pair(tX + 1, tY + 1)
                else Pair(tX+1, tY-1)
            }
            tX - hX > 1 -> {
                if (hY == tY) Pair(tX - 1, tY)
                else if (hY > tY) Pair(tX - 1, tY + 1)
                else Pair(tX - 1, tY-1)
            }
            hY - tY > 1 -> {
                if (hX == tX) Pair(tX, tY + 1)
                else if (hX > tX) Pair(tX + 1, tY + 1)
                else Pair(tX - 1, tY + 1)
            }
            tY - hY > 1 -> {
                if (hX == tX) Pair(tX, tY - 1)
                else if (hX > tX) Pair(tX + 1, tY - 1)
                else Pair(tX - 1, tY - 1)
            }
            else -> Pair(tX, tY)
        }
    }

    private fun part2(motions: List<Motion>) {
        val singleMotions = motions.flatMap { motion ->
            List(motion.spaces) {
                motion.direction
            }
        }

        // head is positions[0], tail is positions[9]
        val positions = Array(10) {
            Pair(0,0)
        }
        val tailVisited = mutableSetOf<Pair<Int, Int>>()

        singleMotions.forEach {
            var (hX, hY) = positions[0]
            when (it) {
                Direction.U -> hY += 1
                Direction.D -> hY -= 1
                Direction.R -> hX += 1
                Direction.L -> hX -= 1
            }
            positions[0] = Pair(hX, hY)

            val newTail = propagateHeadMoved1(positions)
            tailVisited.add(newTail)
        }

        println(tailVisited.count())
    }

    private fun propagateHeadMoved1(positions: Array<Pair<Int, Int>>): Pair<Int, Int> {
        for (i in 1..9) {
            val (hX, hY) = positions[i-1]
            val (tX, tY) = positions[i]
            positions[i] = tailCoordsAfterHeadMoved1(hX, hY, tX, tY)
        }
        return positions[9]
    }
}