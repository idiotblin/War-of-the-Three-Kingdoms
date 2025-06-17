package org.example

class LiuBei : General("Liu Bei") {
    override val maxHP: Int = 5
    var state: State = UnhealthyState()
}

class SunQuan : General("Sun Quan") {
    override val maxHP: Int = 5
}

class CaoCao : WeiGeneral("Cao Cao") {
    override val maxHP: Int = 5

    override fun beingAttacked(source: General?): Boolean? {
        println("$name is being attacked.")
        println("[Entourage] $name activates Lord Skill Entourage.")
        if (!handleRequest()) {
            return super.beingAttacked(null)
        }
        return true
    }
}