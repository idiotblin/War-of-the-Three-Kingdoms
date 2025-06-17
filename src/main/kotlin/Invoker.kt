package org.example

class Invoker {
    private var command: Command? = null

    fun setCommand(command: Command) {
        this.command = command
    }

    fun executeCommand(target: General?) {
        command?.execute(target)
    }
}