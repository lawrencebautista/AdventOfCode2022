import kotlin.math.max

class Day13 {
    fun execute() {
        val inputString = Utils.getInput("${this.javaClass.simpleName.lowercase()}.txt")

//        part1(inputString)
        part2(inputString)
    }

    @Suppress("UNCHECKED_CAST")
    fun parseSignal(string: String): List<Any> {
        val stack: MutableList<Any> = mutableListOf()
        var current: MutableList<Any>? = null

        val iterator = string.iterator()
        var builder = StringBuilder()

        while(iterator.hasNext()) {
            val c = iterator.nextChar()
            when {
                c == '[' -> {
                    val list = mutableListOf<Any>()
                    if (current != null) {
                        stack.add(current)
                        current.add(list)
                    }
                    current = list
                }
                c == ']' -> {
                    builder = checkAndResetBuilder(builder, current)
                    if (stack.isEmpty()) return current!!
                    current = stack.removeLast() as MutableList<Any>
                }
                c.isDigit() -> {
                    builder.append(c)
                }
                c == ',' -> {
                    builder = checkAndResetBuilder(builder, current)
                }
                else -> throw IllegalStateException("Illegal char $c")
            }
        }

        throw IllegalStateException("Did not close properly")
    }

    private fun checkAndResetBuilder(
        builder: StringBuilder,
        current: MutableList<Any>?
    ): StringBuilder {
        if (builder.isNotEmpty()) {
            val num = builder.toString().toInt()
            current?.add(num)
            builder.clear()
        }
        return builder
    }
    private fun part1(inputString: String) {
        val pairsInput = inputString.split("\n\n").filterNot { it.isEmpty() }
        val pairs: List<Pair<List<Any>, List<Any>>> = pairsInput.map {
            val line = it.split('\n')
            val first = parseSignal(line[0])
            val second = parseSignal(line[1])

            Pair(first, second)
        }


        var sum = 0
        pairs.forEachIndexed { pairIndex, pair ->
            val left = pair.first
            val right = pair.second

            val adjIndex = pairIndex + 1

            when (isCorrectOrder(left, right)) {
                true -> {
                    sum += adjIndex
                    println("$adjIndex true")
                }
                false -> {
                    println("$adjIndex false")
                }
                null -> {
                    println("$adjIndex inconclusive")
                }
            }
        }
        println(sum)
    }

    private fun part2(inputString: String) {
        val signals = inputString.split("\n")
            .filterNot { it.isEmpty() }
            .plus("[[2]]")
            .plus("[[6]]")
            .map { parseSignal(it) }

        val sorted = signals.sortedWith { a, b ->
            when (isCorrectOrder(a,b)) {
                true -> -1
                false -> 1
                null -> 0
            }
        }

        val index1 = sorted.indexOf(
            listOf(listOf(2))
        ) + 1
        val index2 = sorted.indexOf(
            listOf(listOf(6))
        ) + 1

        println("${index1*index2}")
    }

    @Suppress("UNCHECKED_CAST")
    private fun isCorrectOrder(leftList: List<Any>, rightList: List<Any>): Boolean? {
        for (i in 0 until max(leftList.size, rightList.size)) {
            if (i >= leftList.size) {
                return true
            }
            if (i >= rightList.size) return false

            val l = leftList[i]
            val r = rightList[i]
            when {
                l is Int && r is Int -> {
                    when {
                        l < r -> return true
                        l > r -> return false
                    }
                }
                l is List<*> && r is List<*> -> {
                    val subIsCorrect = isCorrectOrder(l as List<Any>, r as List<Any>)
                    if (subIsCorrect != null) return subIsCorrect
                }
                else -> {
                    val newL = if (l is Int) listOf<Any>(l) else l
                    val newR = if (r is Int) listOf<Any>(r) else r
                    val subIsCorrect = isCorrectOrder(newL as List<Any>, newR as List<Any>)
                    if (subIsCorrect != null) return subIsCorrect
                }
            }
        }
        return null
    }
}