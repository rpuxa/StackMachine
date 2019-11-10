import javax.xml.parsers.DocumentBuilderFactory
import kotlin.collections.ArrayList

class Direction(val state: State, val terminal: Char) {
    override fun toString(): String {
        return "$terminal${state.name}"
    }
}

open class State(val id: Int, val directions: ArrayList<Direction>) {

    open val name = if (id == 0) "START" else ('A' + (id % 25)).toString() + ('1' + (id / 25))


    var finish = false

    override fun hashCode(): Int {
        return id
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
}


fun main() {
    val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("libs/grapho.jff")
    val nodes = doc.documentElement.childNodes.item(3).childNodes
    val states = (0 until nodes.length).flatMap {
        val item = nodes.item(it)
        if (item.nodeName == "state")
            listOf(item)
        else
            emptyList()
    }.map {
        State(it.attributes.getNamedItem("id").nodeValue.toInt(), ArrayList())
    }

    (0 until nodes.length).flatMap {
        val item = nodes.item(it)
        if (item.nodeName == "transition")
            listOf(item)
        else
            emptyList()
    }.map {
        Triple(
            it.childNodes.item(1).childNodes.item(0).nodeValue.toInt(),
            it.childNodes.item(3).childNodes.item(0).nodeValue.toInt(),
            it.childNodes.item(5).childNodes.item(0).nodeValue.first()
        )
    }.forEach { triple ->
        val from = states.first { triple.first == it.id }
        val to = states.first { triple.second == it.id }
        from.directions.add(Direction(to, triple.third))
        if (triple.third == END_TERMINAL)
            to.finish = true
    }

    states.forEach(::printGraph)
}

fun printEnum(state: State) {
    println(state.name + ",")
}

fun printGraph(state: State) {
    state.directions.forEach {
        val terminal: Any = when (it.terminal) {
            NEW_LINE -> "new_line"
            SPACE -> "_"
            END_TERMINAL -> ";"
            PLUS -> "+"
            MULT -> "*"
            DIGIT -> "0|..|9"
            LETTER -> "a|..|z"
            LETTER_OR_DIGIT -> "a|..|z|0|..|9"
            else -> it.terminal
        }
        println(state.name + " -> " + it.state.name + "[label = \"${terminal}\"];")
    }
}

fun printTable(state: State) {
    println(state.name + " -> " + state.directions.joinToString(" | "))
}

fun printCase(state: State) {
    if (state.finish) {
        return
    }

    println("case ${state.name}:")
    for (it in state.directions) {
        val s = when (it.terminal) {
            NEW_LINE -> "symbol=='\\n'"
            SPACE -> "symbol==' '"
            END_TERMINAL -> "symbol==0"
            PLUS -> "symbol=='+'"
            MULT -> "symbol=='*'"
            DIGIT -> "Character.isDigit(symbol)"
            LETTER -> "Character.isLetter(symbol)"
            LETTER_OR_DIGIT -> "Character.isLetterOrDigit(symbol)"
            else -> "symbol=='${it.terminal}'"
        }
        println("if ($s)")
        println("return States.${it.state.name};")
    }
    println("break;\n")
}