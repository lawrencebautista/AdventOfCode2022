class Day03 {
    fun execute() {
        val inputString = Utils.getInput("day03.txt")
        val lines = inputString.split('\n').filterNot { it.isEmpty() }
        part01(lines)
        val groups = lines.withIndex().groupBy {
            it.index / 3
        }.map { entry ->
            entry.value.map { it.value }
        }
        part02(groups)
    }

    private fun part01(lines: List<String>) {
        val total = lines.fold(0) { sum, line ->
            val length = line.length
            val first = line.substring(0, length/2)
            val second = line.substring(length/2, length)

            val charSet = first.toSet()

            val priority = second.find {
                charSet.contains(it)
            }?.priority() ?: 0

            sum + priority
        }

        println(total)
    }

    private fun part02(groups: List<List<String>>) {
        val total = groups.fold(0) { sum, group ->
            val charSet1 = group[0].toSet()
            val charSet2 = group[1].toSet()

            val priority = group[2].find {
                charSet1.contains(it) && charSet2.contains(it)
            }?.priority() ?: 0
            sum + priority
        }

        println(total)
    }

    private fun Char.priority(): Int {
        return if (this.isLowerCase()) {
            this.code - 'a'.code + 1
        } else {
            this.code - 'A'.code + 27
        }
    }
}