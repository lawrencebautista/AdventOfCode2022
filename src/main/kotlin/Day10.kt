import java.lang.IllegalStateException
import java.lang.Integer.max

class Day10 {
    fun execute() {
        val inputString = Utils.getInput("day10.txt")
        val lines = inputString.split("\n").filterNot { it.isEmpty() }
//        part1(lines)
        part2(lines)
    }
    private fun part1(lines: List<String>) {
        var register = 1
        val signalRecord = mutableListOf(register)
        lines.forEach { line ->
            when {
                line == "noop" -> {
                    signalRecord.add(register)
                }
                line.startsWith("addx") -> {
                    signalRecord.add(register)
                    val toAdd = line.split(' ')[1].toInt()
                    register += toAdd
                    signalRecord.add(register)
                }
            }
        }
        // signalrecord[x] is value of register AFTER xth command
        // value of register during xth command is signalrecord[x-1]
        val indices = listOf(20, 60, 100, 140, 180, 220)
        val sumSignalStrengths = indices.fold(0) { sum, i ->
            sum + (signalRecord[i-1] * i)
        }
        println(signalRecord)
        println(sumSignalStrengths)
    }

    private fun part2(lines: List<String>) {
        var register = 1
        val signalRecord = mutableListOf(register)
        lines.forEach { line ->
            when {
                line == "noop" -> {
                    signalRecord.add(register)
                }

                line.startsWith("addx") -> {
                    signalRecord.add(register)
                    val toAdd = line.split(' ')[1].toInt()
                    register += toAdd
                    signalRecord.add(register)
                }
            }
        }

        for(drawingPosition in 0..239) {
            if ((drawingPosition) % 40 == 0) println()
            val pos = drawingPosition % 40
            val char = if (pos >= signalRecord[drawingPosition]-1 &&
                pos <= signalRecord[drawingPosition]+1)
            '#' else '.'
            print(char)
        }
        println()
    }
}