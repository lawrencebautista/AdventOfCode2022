class Day11 {
    fun execute() {
        val inputString = Utils.getInput("day11.txt")
        val monkeyStrings = inputString.split("\n\n").filterNot { it.isEmpty() }

        val monkeys = monkeyStrings.map { monkeyString ->
            val lines = monkeyString.split('\n')
            val items = lines[1].trim()
                .removePrefix("Starting items: ")
                .filterNot { it == ',' }
                .split(' ')
                .map {
                    it.toLong()
                }

            val operation = lines[2].trim()
                .removePrefix("Operation: new = old ")
                .split(' ')
            val operator: (Long, Long) -> Long = when (operation[0]) {
                "+" -> Long::plus
                "*" -> Long::times
                else -> throw IllegalArgumentException()
            }
            val operand = operation[1].toLongOrNull()

            val inspection: (Long) -> Long = { old ->
                operator(old, operand ?: old)
            }

            val divisibleBy = lines[3].trim()
                .removePrefix("Test: divisible by ")
                .toLong()

            val trueTarget = lines[4].trim()
                .removePrefix("If true: throw to monkey ")
                .toInt()

            val falseTarget = lines[5].trim()
                .removePrefix("If false: throw to monkey ")
                .toInt()

            Monkey(items, inspection, divisibleBy, trueTarget, falseTarget)
        }

//        part1(monkeys)
        part2(monkeys)
    }

    class Monkey(
        startingItems: List<Long>,
        val inspection: (Long) -> Long,
        val testDivisibleBy: Long,
        val trueTarget: Int,
        val falseTarget: Int,
    ) {
        val currentItems = ArrayDeque(startingItems)
    }
    private fun part1(monkeys: List<Monkey>) {
        val inspectCounts = Array(monkeys.size) { 0 }
        for (round in 1..20) {
            monkeys.forEachIndexed { monkeyIndex, monkey ->
                while (monkey.currentItems.isNotEmpty()) {
                    val item = monkey.currentItems.removeFirst()
                    inspectCounts[monkeyIndex]++
                    val worry = monkey.inspection(item) / 3
                    val test = worry % monkey.testDivisibleBy == 0L
                    val target = if (test) {
                        monkeys[monkey.trueTarget]
                    } else {
                        monkeys[monkey.falseTarget]
                    }
                    target.currentItems.addLast(worry)
                }
            }
        }

        val sortedCounts = inspectCounts.toList().sorted().reversed()
        println(sortedCounts)
        println(sortedCounts[0] * sortedCounts[1])
    }

    private fun part2(monkeys: List<Monkey>) {
        val lcm = monkeys.fold(1L) { prod, monkey ->
            prod * monkey.testDivisibleBy
        }
        val inspectCounts = Array(monkeys.size) { 0L }
        for (round in 1..10000) {
            monkeys.forEachIndexed { monkeyIndex, monkey ->
                while (monkey.currentItems.isNotEmpty()) {
                    val item = monkey.currentItems.removeFirst()
                    inspectCounts[monkeyIndex]++
                    val worry = monkey.inspection(item) % lcm
                    val test = worry % monkey.testDivisibleBy == 0L
                    val target = if (test) {
                        monkeys[monkey.trueTarget]
                    } else {
                        monkeys[monkey.falseTarget]
                    }
                    target.currentItems.addLast(worry)
                }
            }
        }
        val sortedCounts = inspectCounts.toList().sorted().reversed()
        println(sortedCounts)
        println(sortedCounts[0] * sortedCounts[1])
    }
}