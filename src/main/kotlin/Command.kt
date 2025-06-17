package org.example

interface Command {
    fun execute(target: General?)
}