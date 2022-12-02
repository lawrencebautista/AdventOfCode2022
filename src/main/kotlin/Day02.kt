import java.lang.IllegalStateException
import java.lang.Integer.max

class Day02 {
    fun execute() {
        val inputString = Utils.getInput("day02.txt")
        val lines = inputString.split("\n").filterNot { it.isEmpty() }
        part1(lines)
        part2(lines)
    }
    private fun part1(lines: List<String>) {
        val result = lines.map {
            it.toScore1()
        }.sum()
        println(result)
    }

    private fun part2(lines: List<String>) {
        val result = lines.map {
            it.toScore2()
        }.sum()
        println(result)
    }
    private fun String.toScore1(): Int {
        return when (this) {
            "A X" -> 4
            "A Y" -> 8
            "A Z" -> 3
            "B X" -> 1
            "B Y" -> 5
            "B Z" -> 9
            "C X" -> 7
            "C Y" -> 2
            "C Z" -> 6
            else -> throw IllegalStateException("Can't parse $this")
        }
    }

    private fun String.toScore2(): Int {
        return when (this) {
            "A X" -> 3
            "A Y" -> 4
            "A Z" -> 8
            "B X" -> 1
            "B Y" -> 5
            "B Z" -> 9
            "C X" -> 2
            "C Y" -> 6
            "C Z" -> 7
            else -> throw IllegalStateException("Can't parse $this")
        }
    }
}