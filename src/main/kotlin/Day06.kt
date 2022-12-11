class Day06 {
    fun execute() {
        val inputString = Utils.getInput("day06.txt")
//        part01(inputString)
        part02(inputString)
    }

    private fun part01(input: String) {
        var start = 0
        var end = 4

        while (end < input.length) {
            val array = input.subSequence(start, end)
            if (array[0] == array[1] || array[0] == array[2] || array[0] == array[3] ||
                array[1] == array[2] || array[1] == array[3] || array[2] == array[3]
            ) {
                start++
                end++
            } else break
        }
        println(end)
    }

    private fun part02(input: String) {
        var start = 0
        var end = 14

        val charMap = mutableMapOf<Char, Int>()
        input.subSequence(start, end).forEach {
            val currentCount = charMap.getOrDefault(it, 0)
            charMap[it] = currentCount + 1
        }

        println(charMap)

        start++
        end++

        while (end < input.length) {
            val toRemove = input[start-1]
            charMap[toRemove] = charMap[toRemove]!! - 1

            val toAdd = input[end-1]
            charMap[toAdd] = charMap.getOrDefault(toAdd, 0) + 1

            val hasDuplicates = charMap.any {
                it.value > 1
            }

            if (start < 5) {
                println("${input.substring(start, end)} $charMap")
            }

            if (hasDuplicates) {
                start++
                end++
            } else break
        }
        println(end)
    }
}