import java.util.*

class Day05 {
    fun execute() {
        val inputString = Utils.getInput("day05.txt")
        val split = inputString.split("\n\n")
        val stackInput = split[0]
        val commandsInput = split[1]

        val stacks = Array(10) {
            ArrayDeque<Char>()
        }

        stackInput.split('\n').forEach {
            var start = 0
            var index = 1
            while (start+2 < it.length) {
                val char = it.substring(start, start+3).find { c -> c.isLetter()}
                if (char != null) stacks[index].addFirst(char)
                index++
                start += 4
            }
        }

        val commands = commandsInput.split('\n').filterNot { it.isEmpty() }
//        part01(stacks, commands)
        part02(stacks, commands)
    }

    private fun printStacks(stacks: Array<ArrayDeque<Char>>) {
        stacks.forEach {
            println(it.toString())
        }

        val output = stacks.fold("") { s, queue ->
            val last = queue.lastOrNull()

            if (last == null)
                s
            else
                s + last
        }
        println(output)
    }

    private fun part01(stacks: Array<ArrayDeque<Char>>, commands: List<String>) {
        commands.forEachIndexed { index, command ->
            val words = command.split(' ')
            val moveCount = words[1].toInt()
            val from = words[3].toInt()
            val to = words[5].toInt()

            for (i in 1..moveCount) {
                val c = stacks[from].removeLast()
                stacks[to].addLast(c)
            }
        }
        printStacks(stacks)
    }

    private fun part02(stacks: Array<ArrayDeque<Char>>, commands: List<String>) {
        commands.forEachIndexed { index, command ->
            val words = command.split(' ')
            val moveCount = words[1].toInt()
            val from = words[3].toInt()
            val to = words[5].toInt()

            val toMove = mutableListOf<Char>()
            for (i in 1..moveCount) {

                val c = stacks[from].removeLast()
                toMove.add(0, c)
            }
            stacks[to].addAll(toMove)
        }
        printStacks(stacks)
    }
}