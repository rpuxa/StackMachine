package asd

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.test.fail

class Direction(val state: State, val terminal: Char) {
    override fun toString(): String {
        return "$terminal${state.name}"
    }
}

open class State {

    open val id = globalId++
    open val name = ('A' + (id % 25)).toString() + ('1' + (id / 25))
    val directions = ArrayList<Direction>()

    init {
        allStates.add(this)
    }

    fun isLooped(): Boolean {
        val checked = HashSet<State>()
        val queue = ArrayDeque<State>()
        queue.add(this)

        while (queue.isNotEmpty()) {
            val state = queue.pollFirst()!!
            if (state in checked) continue
            checked.add(state)
            state.directions.forEach {
                if (it.state == this) return true
                queue.addLast(it.state)
            }
        }

        return false
    }

    override fun hashCode(): Int {
        return id
    }




    fun newDirection(state: State, terminal: Char): State {
        val other = directions.find { it.terminal == terminal }
        if (other != null) return other.state
        directions.add(Direction(state, terminal))
        return state
    }

    override fun toString(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as State

        if (id != other.id) return false

        return true
    }

    companion object {
        var globalId = 0
        val allStates = ArrayList<State>()
    }
}

object Finish : State() {
    override val id: Int get() = -1

    override val name: String
        get() = "Finish"
}

class States : MutableSet<State> by HashSet<State>() {

    fun clone(): States = States().apply {
        addAll(this@States)
    }

    fun set(states: States) {
        clear()
        addAll(states)
    }

    companion object {
        fun single(state: State) = States().apply {
            add(state)
        }
    }
}

class Sequence(list: List<Any>) : List<Any> by list

fun consistently(vararg e: Any) = Sequence(e.toList())

class Branch(list: List<Any>) : List<Any> by list

fun parallel(vararg e: Any) = Branch(e.toList())

class ZeroOrMore(val element: Any)
class ZeroOrOne(val element: Any)


enum class Names {
    START,
    EXPR,
    SIMPLE_EXPR,
    STMT,
    TERM,
    LOG_OPER,
    MATH_OPER,
    REL_OPER,
    ANY_NUMB,
    ID,
    INT_NUMB,
    FIX_POINT_NUM,
    REAL_NUM,
    PLUS,
    MINUS
}

enum class Specials {
    ZERO_OR_MORE_SPACES,
    ONE_SPACE_OR_MORE,
    END
}

const val SPACE = '_'
const val END_TERMINAL = ';'
const val MERGE_SPECIAL_SYMBOL = 'ε'
const val NEW_LINE = 'э'
const val DIGIT = 'ц'
const val SYMBOL = 'б'
const val SYMBOL_OR_DIGIT = 'ж'

val transitions = mapOf(
    Names.START to consistently(
        Specials.ZERO_OR_MORE_SPACES,
        "do",
        Specials.ONE_SPACE_OR_MORE,
        "while",
        Specials.ONE_SPACE_OR_MORE,
        Names.EXPR,
        Specials.ZERO_OR_MORE_SPACES,
        "$NEW_LINE",
        Specials.ZERO_OR_MORE_SPACES,
        Names.STMT,
        Specials.ZERO_OR_MORE_SPACES,
        "$NEW_LINE",
        Specials.ZERO_OR_MORE_SPACES,
        "loop",
        Specials.ZERO_OR_MORE_SPACES
    ),
    Names.EXPR to consistently(
        Names.SIMPLE_EXPR,
        ZeroOrMore(
            consistently(
                Specials.ONE_SPACE_OR_MORE,
                Names.LOG_OPER,
                Specials.ONE_SPACE_OR_MORE,
                Names.SIMPLE_EXPR
            )
        )
    ),
    Names.SIMPLE_EXPR to consistently(
        Names.TERM,
        Specials.ZERO_OR_MORE_SPACES,
        Names.REL_OPER,
        Specials.ZERO_OR_MORE_SPACES,
        Names.TERM
    ),
    Names.STMT to consistently(
        Names.ID,
        Specials.ZERO_OR_MORE_SPACES,
        "=",
        Specials.ZERO_OR_MORE_SPACES,
        Names.TERM,
        ZeroOrMore(
            consistently(Specials.ZERO_OR_MORE_SPACES, Names.MATH_OPER, Specials.ZERO_OR_MORE_SPACES, Names.TERM)
        )
    ),
    Names.TERM to parallel(Names.ID, Names.ANY_NUMB),
    Names.LOG_OPER to parallel("and", "or", "xor"),
    Names.MATH_OPER to parallel("+", "-", "*", "/"),
    // TODO fix real oper
    Names.REL_OPER to parallel(">", ">>"),//"=", "<", "<=", "<>", ">", ">="),
    Names.ANY_NUMB to consistently(
        parallel(Names.PLUS, Names.MINUS, DIGIT),
        ZeroOrMore(DIGIT),
        ZeroOrOne(
            consistently(
                '.',
                DIGIT,
                ZeroOrMore(DIGIT)
            )
        )
    ),
    Names.PLUS to consistently("+", DIGIT),
    Names.MINUS to consistently("-", DIGIT),
    Names.ID to consistently(SYMBOL, consistently(SYMBOL_OR_DIGIT))
)

fun getStates(e: Any, startStates: States): States {
    var newStates = startStates.clone()
    when (e) {
        is Sequence -> {
            e.forEach {
                newStates = getStates(it, newStates)
            }
        }

        is Branch -> {
            newStates.clear()
            e.forEach {
                newStates.addAll(getStates(it, startStates))
            }
        }

        is Names -> {
            newStates = getStates(transitions[e] ?: error("Unknown name"), startStates)
        }

        is String -> {
            newStates = getStates(Sequence(e.toList()), newStates)
        }

        is Char -> {
            val state = State()
            newStates.clone()
            newStates.addAll(newStates.map { it.newDirection(state, e) })
        }

        is ZeroOrOne -> {
            newStates.addAll(getStates(e.element, startStates))
        }

        is ZeroOrMore -> {
            val loopStates = States()
            newStates.forEach {
                val loopStartState = getStates(e.element, States.single(it))
                val loopEndState = getStates(e.element, loopStartState)
                if (loopStartState.size != loopEndState.size) fail("Предположение неверно!")
                val startIterator = loopStartState.iterator()
                val endIterator = loopEndState.iterator()
                for (i in loopStartState.indices) {
                    endIterator.next().newDirection(startIterator.next(), MERGE_SPECIAL_SYMBOL)

                }
                loopStates.addAll(loopStartState)
            }
            newStates.addAll(loopStates)
        }

        Specials.ZERO_OR_MORE_SPACES -> {
            newStates = getStates(ZeroOrMore(SPACE), newStates)
        }

        Specials.ONE_SPACE_OR_MORE -> {
            newStates = getStates(SPACE, newStates)
            newStates = getStates(Specials.ZERO_OR_MORE_SPACES, newStates)
        }

        Specials.END -> {
            newStates.forEach { it.newDirection(Finish, END_TERMINAL) }
            return States.single(Finish)
        }

        else -> error(e)
    }

    return newStates
}

fun printTable(state: State) {
    println(state.name + " -> " + state.directions.joinToString(" | "))
}

fun printCase(state: State) {
    if (state === Finish) {
        println("case -1: return;")
        return
    }

    println("case ${state.id}:")
    for (it in state.directions) {
        val s = when (it.terminal) {
            NEW_LINE -> "symbol=='\\n'"
            SPACE -> "symbol==' '"
            END_TERMINAL -> "symbol==0"
            DIGIT -> "Character.isDigit(symbol)"
            SYMBOL -> "Character.isLetter(symbol)"
            SYMBOL_OR_DIGIT -> "Character.isLetterOrDigit(symbol)"
            MERGE_SPECIAL_SYMBOL -> fail()
            else -> "symbol=='${it.terminal}'"
        }
        println("if ($s) {")
        println(if (it.state.id == state.id) "return;" else "state = ${it.state.id};")
        print("} else ")
    }
    println("{")
    println("error = true;")
    println("} return;")
}

fun removeMergeSymbol() {
    for (j in State.allStates.indices.reversed()) {
        val state = State.allStates[j]
        if (state.directions.any { it.terminal == MERGE_SPECIAL_SYMBOL }) {
            State.allStates.removeAt(j)
            continue
        }

        for (i in state.directions.indices.reversed()) {
            val direction = state.directions[i]
            if (direction.state.directions.any { it.terminal == MERGE_SPECIAL_SYMBOL }) {
                if (direction.state.directions.any { it.terminal != MERGE_SPECIAL_SYMBOL })
                    fail()

                state.directions.removeAt(i)

                state.directions.addAll(
                    direction.state.directions.map { Direction(it.state, direction.terminal) }
                )

            }
        }
    }
}

fun main() {
    val start = State()
    getStates(Branch( transitions[Names.REL_OPER] as List<Any> + Specials.END), States.single(start))
    removeMergeSymbol()
    State.allStates.sortedBy { it.id }.forEach(::printCase)
}