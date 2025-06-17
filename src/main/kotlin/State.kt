package org.example

interface State {
    fun playNextCard(liuBei: LiuBei)
}

class HealthyState : State {
    override fun playNextCard(liuBei: LiuBei) {
        println("Liu Bei is healthy.")
        val opponents = GeneralManager.getOpponents(liuBei)
        val target = liuBei.strategy?.whomToAttack(liuBei, opponents)
        if (target != null) {
            println("Liu Bei spends a card to attack ${target.name}")
            target.beingAttacked(null)
        }
    }
}

class UnhealthyState : State {
    override fun playNextCard(liuBei: LiuBei) {
        println("Liu Bei is not healthy.")
        if (liuBei.cards.size >= 2) {
            println("[Benevolence] Liu Bei gives away two cards and recovers 1 HP, now his HP is ${liuBei.currentHP + 1}.")
            println("Liu Bei is now healthy.")
            liuBei.cards.removeAt(0)
            liuBei.cards.removeAt(0)
            liuBei.currentHP += 1
            liuBei.state = HealthyState()
        }
    }
}