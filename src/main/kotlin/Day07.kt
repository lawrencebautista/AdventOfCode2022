import java.lang.IllegalStateException

class Day07 {
    fun execute() {
        val inputString = Utils.getInput("day07.txt")
        val lines = inputString.split('\n').filterNot { it.isEmpty()}

        val root = createFileTree(lines)
//        root.debug()
//        part01(root)
        part02(root)
    }

    private fun createFileTree(lines: List<String>): Dir {
        val root = Dir("/", null, 0)
        var currentDir = root
        var lineNumber = 1
        while (lineNumber < lines.size) {
            val line = lines[lineNumber]
            if (line.startsWith('$')) {
                val tokens = line.split(' ')
                if (tokens[1] == "ls") {
                    lineNumber = processLs(currentDir, lines, lineNumber)
                } else if (tokens[1] == "cd") {
                    currentDir = if (tokens[2] == "..") {
                        currentDir.parent ?: throw IllegalStateException("dir ${currentDir.name} has no parent")
                    } else {
                        currentDir.getDir(tokens[2])
                    }
                    lineNumber++
                } else {
                    throw IllegalStateException("Invalid command: ${tokens[1]}")
                }
            }
        }
        return root
    }

    // returns line number after "ls" is processed
    fun processLs(dir: Dir, lines: List<String>, lineNumber: Int): Int {
        var currentLineNumber = lineNumber + 1
        var currentLine: List<String>

        while (currentLineNumber < lines.size) {
            currentLine = lines[currentLineNumber].split(' ')

            if (currentLine[0] == "dir") {
                dir.add(Dir(currentLine[1], dir, 0))
            } else if (currentLine[0] == "$") {
                break
            } else {
                dir.fileSizes += currentLine[0].toInt()
            }

            currentLineNumber++
        }
        return currentLineNumber
    }

    private fun part01(root: Dir) {
        val toAdd = mutableListOf<Int>()
        root.calculateTotalSize1(toAdd)

        println(toAdd.sum())
    }


    private fun part02(root: Dir) {
        val totalSizes = mutableListOf<Int>()
        val usedSpace = root.calculateTotalSize2(totalSizes)
        totalSizes.sort()

        val minToDelete = usedSpace - 40000000
        val toDelete = totalSizes.first {
            it >= minToDelete
        }
        println(toDelete)
    }
}

class Dir(
    val name: String,
    val parent: Dir?,
    var fileSizes: Int,
    val subDirs: MutableList<Dir> = mutableListOf()
) {
    var totalSize: Int? = null
    fun add(dir: Dir) {
        subDirs.add(dir)
    }

    fun getDir(name: String): Dir {
        return subDirs.find { it.name == name } ?: throw IllegalStateException("No dir in ${this.name} with name $name")
    }

    // Calculates total size and adds to list if under 100000
    fun calculateTotalSize1(list: MutableList<Int>): Int {
        val total = fileSizes + subDirs.fold(0) { sum, dir ->
            sum + dir.calculateTotalSize1(list)
        }
        if (total <= 100000) {
            list.add(total)
        }
        return total

    }

    fun calculateTotalSize2(list: MutableList<Int>): Int {
        val total = fileSizes + subDirs.fold(0) { sum, dir ->
            sum + dir.calculateTotalSize2(list)
        }
        list.add(total)
        return total

    }

    fun debug() {
        println("Dir: $name, parent=${parent?.name}, subDirs=${subDirs.size}, fileSizes=$fileSizes")
        subDirs.forEach { it.debug() }
    }
}