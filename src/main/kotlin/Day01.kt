import java.lang.Integer.max

class Day01 {
    fun execute() {
        val inputString = Utils.getInput("day01.txt")
        val groups = inputString.split("\n\n")
        val elves = groups.map { group ->
            group.split('\n').mapNotNull {
                it.toIntOrNull()
            }
        }
        part1(elves)
        part2(elves)
    }
    private fun part1(elves: List<List<Int>>) {
        val result = elves.fold(0) { currentMax, calories  ->
            max(calories.sum(), currentMax)
        }
        println(result)
    }

    private fun part2(elves: List<List<Int>>) {
        val sortedMax = elves.map {
            it.sum()
        }.sorted().asReversed()

        println(sortedMax[0] + sortedMax[1] + sortedMax[2])
    }
}