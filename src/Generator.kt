/*
import asd.State

class Direction(val toState: State, val terminal: Char?) {
    override fun toString(): String {
        return (terminal ?: "ε").toString() + ";" + toState.getName()
    }
}

open class State(val id: Int, val direction: MutableList<Direction> = ArrayList()) {

    open fun getName() = newNames[id] ?: ('A' + (id % 25)).toString() + ('1' + (id / 25))

    override fun toString(): String {
        return buildString {
            direction.forEach {
                append(""" "${getName()}" ->"${it.toState.getName()}" [label = "${it.terminal}"]; """)
            }
        }
    }
}

object Finish : State(-1) {
    override fun getName() = "Finish"
}

const val NEW_LINE = 'э'
const val tester.DIGIT = 'ц'
const val SYMBOL = 'б'
const val SYMBOL_OR_DIGIT = 'ж'

const val tester.START = 1
const val EXPR = 2
const val SIMPLE_EXPR = 3
const val STMT = 4
const val TERM = 5
const val LOG_OPER = 6
const val MATH_OPER = 7
const val REL_OPER = 8
const val ANY_NUMB = 9
const val ID = 10
const val INT_NUMB = 11
const val FIX_POINT_NUM = 12
const val REAL_NUM = 13
const val tester.PLUS = 14
const val MINUS = 15

val newNames = HashMap<Int, String>()

fun tester.main() {
    fun filter(e: List<Any>) =
        e.flatMap {
            if (it is String) {
                it.asIterable()
            } else {
                listOf(it)
            }
        }

    fun filter(vararg e: Any) = filter(e.toList())


    fun or(vararg e: Any) =
        filter(e.toList()).toTypedArray()


    val map = mapOf(
            //tester.START to filter(filter("1"), filter("2"), ";")
        tester.START to filter("_do!while!", EXPR, "_${NEW_LINE}_", STMT, "_${NEW_LINE}_loop_"),
        EXPR to filter(SIMPLE_EXPR, filter("!", LOG_OPER, "!", SIMPLE_EXPR)),
        SIMPLE_EXPR to filter(TERM, "_", REL_OPER, "_", TERM),
        STMT to filter(ID, "_=_", TERM, filter("_", MATH_OPER, "_", TERM)),
        TERM to filter(or(ID, REAL_NUM)),
        LOG_OPER to filter(or("and", "or", "xor")),
        MATH_OPER to filter(or("+", "-", "*", "/")),
        REL_OPER to filter(or("=", "<", "<=", "<>", ">", ">=")),
        ANY_NUMB to filter(or(INT_NUMB, REAL_NUM)),
        ID to filter(SYMBOL, filter(SYMBOL_OR_DIGIT)),
        INT_NUMB to filter(or(tester.PLUS, MINUS, tester.DIGIT), filter(tester.DIGIT)),
        //            FIX_POINT_NUM to filter("+ε-", "0.", "0..9", filter("0..9")),
        REAL_NUM to filter(or(tester.PLUS, MINUS, tester.DIGIT), filter(tester.DIGIT), ".", tester.DIGIT, filter(tester.DIGIT)),
        tester.PLUS to filter("+", tester.DIGIT),
        MINUS to filter("-", tester.DIGIT)
    )
    var id = 0

    var currentState = asd.State(id++)
    val startState = currentState

    fun newDirection(state: State, terminal: Char?) {
        currentState.direction.add(Direction(state, terminal))
    }

    fun toNewState(terminal: Char?) {
        val newState = asd.State(id++)
        currentState.direction.add(Direction(newState, terminal))
        currentState = newState
    }

    var lastSpecial = false
    fun recursive(line: Int) {

        fun loop(iterable: Any) {

            fun iteration(element: Any?) {
                when (element) {
                    is Char -> {
                        when (element) {
                            '_' -> {
                                if (lastSpecial)
                                    toNewState(null)
                                newDirection(currentState, '_')
                                lastSpecial = true
                            }
                            '!' -> {
                                toNewState('_')
                                newDirection(currentState, '_')
                                lastSpecial = true
                            }

                            ';' -> {
                                newDirection(Finish, ';')
                            }
                            else -> {
                                toNewState(element)
                                lastSpecial = false
                            }
                        }
                    }

                    is Int -> {
                        recursive(element)
                    }

                    is List<*> -> {
                        if (lastSpecial)
                            toNewState(null)
                        val loopState = currentState
                        loop(element)
                        newDirection(loopState, null)
                        currentState = loopState
                        lastSpecial = true
                    }

                    is Array<*> -> {
                        val start = currentState
                        val end = asd.State(id++)
                        element.forEach { e ->
                            iteration(e)
                            newDirection(end, null)
                            currentState = start
                        }
                        currentState = end
                    }
                }
            }

            when (iterable) {
                is Iterable<*> -> for (it in iterable) {
                    iteration(it)
                }
                is Array<*> -> for (it in iterable) {
                    iteration(it)
                }
                else -> error("WTF")
            }

        }

        loop(map[line] ?: error(""))
    }


    recursive(tester.START)

    val optimized = HashSet<Int>()


    fun removeMergeSymbol(state: State) {
        if (state.id in optimized) return
        optimized.add(state.id)
        if (state.direction.size == 1) {
            val direction = state.direction.first()
            if (direction.terminal == null) {
                newNames[state.id] = direction.toState.getName()
            }
        }

        state.direction.forEach { removeMergeSymbol(it.toState) }
    }

    removeMergeSymbol(startState)

    val printed = HashSet<Int>()

    fun printCase(state: State) {
        println("case ${state.id}:")
        state.direction.forEach {
            val s = when (it.terminal) {
                null ->"symbol=='\\n'"
                NEW_LINE -> "symbol=='\\n'"
                tester.DIGIT -> "Character.isDigit(symbol)"
                SYMBOL -> "Character.isLetter(symbol)"
                SYMBOL_OR_DIGIT -> "Character.isLetterOrDigit(symbol)"
                else -> "symbol=='${it.terminal}'"
            }
            println("if ($s) {")
            println(if (it.toState.id == state.id) "break;" else "state = ${it.toState.id};")
            print("} else ")
        }
        println("{")
        println("error = true;")
        println("}; break;")
    }

    fun printRecursive(state: State) {
        if (state.id !in printed) {
            if (state.id !in newNames)
                println(state)
            printed.add(state.id)
            state.direction.forEach { printRecursive(it.toState) }
        }
    }

    printRecursive(startState)
}


*/
