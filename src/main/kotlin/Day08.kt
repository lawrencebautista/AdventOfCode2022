import kotlin.math.log10

class Day08 {
    fun execute() {
        val inputString = Utils.getInput("day08.txt")
        val lines = inputString.split('\n').filterNot { it.isEmpty()}

        val matrix = Array(99) { index ->
            val line = lines[index]
            line.map {
                it.digitToInt().toByte()
            }.toByteArray()
        }

//        part01(matrix)
        part02(matrix)
    }

    private fun part01(matrix: Array<ByteArray>) {
        val trees = matrix.map { row ->
            row.map {
                Tree(it)
            }.toTypedArray()
        }.toTypedArray()

        processVisibility(trees)

        var visibleCount = 0
        for (i in 0..98) {
            for (j in 0..98) {
                val tree = trees[i][j]
                if (tree.visibleTop || tree.visibleBottom || tree.visibleLeft || tree.visibleRight) {
                    visibleCount++
                }
            }
        }
        println(visibleCount)
    }

    private fun processVisibility(trees: Array<Array<Tree>>) {
        trees.forEach {
            it[0].visibleLeft = true
            it[98].visibleRight = true
        }

        trees[0].forEach {
            it.visibleTop = true
        }

        trees[98].forEach {
            it.visibleBottom = true
        }

        for (i in 1..97) {
            // process visibleLeft
            var maxFromLeft = trees[i][0].height
            for (j in 1..97) {
                if (trees[i][j].height > maxFromLeft) {
                    trees[i][j].visibleLeft = true
                    maxFromLeft = trees[i][j].height
                    if (maxFromLeft == 9.toByte()) break
                }
            }

            // process visibleRight
            var maxFromRight = trees[i][98].height
            for (j in 97 downTo 1) {
                if (trees[i][j].height > maxFromRight) {
                    trees[i][j].visibleRight = true
                    maxFromRight = trees[i][j].height
                    if (maxFromRight == 9.toByte()) break
                }
            }
        }

        for (j in 1..97) {
            var maxFromTop = trees[0][j].height
            for (i in 1..97) {
                if (trees[i][j].height > maxFromTop) {
                    trees[i][j].visibleTop = true
                    maxFromTop = trees[i][j].height
                    if (maxFromTop == 9.toByte()) break
                }
            }

            var maxFromBottom = trees[98][j].height
            for (i in 97 downTo 1) {
                if (trees[i][j].height > maxFromBottom) {
                    trees[i][j].visibleBottom = true
                    maxFromBottom = trees[i][j].height
                    if (maxFromBottom == 9.toByte()) break
                }
            }
        }
    }

    class Tree(
        val height: Byte
    ) {
        var visibleTop = false
        var visibleBottom = false
        var visibleLeft = false
        var visibleRight = false
    }

    private fun part02(matrix: Array<ByteArray>) {
        var maxScore = 0
        for (i in 0..98) {
            for (j in 0..98) {
                val height = matrix[i][j]
                val score = calculateScenicScore(height, i, j, matrix)
                if (score > maxScore) maxScore = score
            }
        }
        println(maxScore)
    }

    private fun calculateScenicScore(height: Byte, i: Int, j: Int, matrix: Array<ByteArray>): Int {
        var topCount = 0
        for (a in i-1 downTo 0) {
            topCount++
            if (matrix[a][j] >= height) break
        }

        var bottomCount = 0
        for (a in (i+1)..98) {
            bottomCount++
            if (matrix[a][j] >= height) break
        }

        var leftCount = 0
        for (a in j-1 downTo 0) {
            leftCount++
            if (matrix[i][a] >= height) break
        }

        var rightCount = 0
        for (a in j+1..98) {
            rightCount++
            if (matrix[i][a] >= height) break
        }

        return topCount * bottomCount * leftCount * rightCount
    }
}