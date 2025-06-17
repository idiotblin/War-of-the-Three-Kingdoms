package org.example

abstract class WeiGeneral(name: String) : General(name) {
    var next: WeiGeneral? = null

    fun handleRequest(): Boolean {
        if (name != "Cao Cao" && hasDodgeCard() && (0..1).random() == 1) {
            println("$name helps dodge the attack by spending a dodge card.")
            return true
        }
        return next?.handleRequest() ?: false
    }
}