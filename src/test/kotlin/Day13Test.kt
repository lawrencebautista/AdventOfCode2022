import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day13Test {

    @Test
    fun parse() {
        val string = "[[34],[43,4,[1,3,5]]]"
        val parsed = Day13().parseSignal(string)
        println(parsed)
    }
}