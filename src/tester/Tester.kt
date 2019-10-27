package tester

const val NUMBER = 'б'
const val START = 'э'
const val ID = 'а'
const val TERM = 'в'
const val REL_OPER = 'е'
const val MATH_OPER = 'к'
const val STMT = 'м'
const val SIMPLE_EXPR = 7815.toChar()
const val LOG_OPER = 7816.toChar()
const val EXPR = 7817.toChar()



fun main() {
    val map = mapOf(
        START to "(п+$NEW_LINE)*doп_whileп_${EXPR}_${NEW_LINE}_${STMT}_${NEW_LINE}_loop(п+$NEW_LINE)*" + END_TERMINAL,
        EXPR to "($SIMPLE_EXPR(п_${LOG_OPER}п_${SIMPLE_EXPR})*)",
        SIMPLE_EXPR to "(${TERM}_${REL_OPER}_${TERM})",
        STMT to "${ID}_=_${TERM}(_${MATH_OPER}_${TERM})*",
        TERM to "(($ID)+($NUMBER))",
        REL_OPER to "(=+<+<=+<>+>+>=)",
        MATH_OPER to "($PLUS+-+$MULT+/)",
        NUMBER to "(($PLUS$DIGIT+-$DIGIT+$DIGIT)$DIGIT*(!+.$DIGIT$DIGIT*))",
        ID to "($LETTER$LETTER_OR_DIGIT*)",
        LOG_OPER to "(and+or+xor)",
        '_' to "п*"
    )

    fun getLine(number: Char): List<Char>? {
       return map[number]?.flatMap {
            val c = getLine(it)
           c?.asIterable() ?: listOf(it)
        }
    }

    println(getLine(START)!!.joinToString(""))
}

const val SPACE = 'п'
const val NEW_LINE = '?'
const val END_TERMINAL = ';'
const val PLUS = 'ъ'
const val MULT = 'ё'
const val DIGIT = 'з'
const val LETTER = 'й'
const val LETTER_OR_DIGIT = 'д'
