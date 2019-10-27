package tester

import java.io.File
import java.io.FileInputStream
import java.nio.charset.Charset
import java.util.*

fun main() {
    val file = File("libs/graph2.jff")
    val fis = FileInputStream(file)

    val scanner = Scanner(fis)

    while (true) {
        val line = scanner.nextLine() ?: return
        if ("<label>" !in line)
            println(line)
    }

    fis.close()

}