package org.example

import AttackCard
import Card
import CardManager
import Duel
import RattanArmor
import WineCard
import kotlin.math.abs
import kotlin.math.min
import kotlin.system.exitProcess

abstract class General(override val name: String) : Player {
    abstract override val maxHP: Int
    override var currentHP: Int = 0
    override var skipPlayPhase: Boolean = false
    override var strategy: Strategy? = null
    override val cards: MutableList<Card> = mutableListOf()
    override var distance: HashMap<Player, Int> = HashMap()
    override var timeDelayCards: MutableList<AcediaCommand> = mutableListOf()
    var equipmentCard: Equipment? = null

    init {
        println("General $name has been created.")
    }

    open fun beingAttacked(source: General?): Boolean? {
        if (name != "Cao Cao")
            println("$name is being attacked.")
        if (equipmentCard is RattanArmor) {
            println("$name is equipped with Rattan Armor, which saves it from usual attacks")
            return true
        }

        if (!dodgeAttack()) {
            currentHP -= 1
            println("$name can't dodge the attack, current HP is $currentHP")
            isKilled(source)
            if (strategy is LordStrategy) {
                (strategy as LordStrategy).notifyObservers("no_dodge")
            }
            if (name == "Liu Bei" && this is LiuBei) {
                if (currentHP == 1) {
                    this.state = UnhealthyState()
                }
            }
            return false
        } else {
            println("$name dodged attack by spending a dodge card.")
            if (strategy is LordStrategy) {
                (strategy as LordStrategy).notifyObservers("dodge")
            }
            return true
        }
    }

    /**
     * Handles the behavior when the player is attacked with a Fire Attack.
     * Deals 1 damage, but if the target is wearing Rattan Armor, the damage is increased by 1 (total 2 damage).
     */
    fun beingFireAttacked() {
        println("$name is being attacked with a Fire Attack.")
        if (equipmentCard !is RattanArmor) {
            currentHP -= 1 // Fire Attack causes 2 damage
            println("$name can't dodge the Fire Attack, current HP is $currentHP")
        } else {
            (equipmentCard as RattanArmor).use()
        }
    }

    fun dodgeAttack(): Boolean {
        if (equipmentCard is EightTrigrams) {
            if ((equipmentCard as EightTrigrams).use()) {
                println("$name dodged the attack with the eight trigrams.")
                return true
            }
        }
        return hasDodgeCard()
    }

    fun hasDodgeCard(): Boolean {
        return (0..99).random() < 15
    }

    fun hasAttackCard(): Boolean {
        return cards.any { it is AttackCard }
    }

    open fun takeTurn() {
        preparationPhase()
        judgementPhase()
        drawPhase()
        playPhase()
        discardPhase()
        finalPhase()
    }

    fun calcDistances() {
        val index = GeneralManager.list.indexOf(this)
        for (i in 0..GeneralManager.list.size - 1) {
            distance[GeneralManager.list[i]] = min(abs(index - i), GeneralManager.list.size - abs(index - i))
        }
    }

    fun preparationPhase() {
        calcDistances()
    }

    fun judgementPhase() {
        for (command in timeDelayCards) {
            val invoker = Invoker()
            invoker.setCommand(command)
            invoker.executeCommand(null)
        }
        timeDelayCards.clear()
    }

    open fun drawPhase() {
        val drawnCards = CardManager.draw(this, 2)
        println("$name draws 2 cards and now has ${cards.size} card(s).")
        if (drawnCards.filter { it !is Equipment }.isNotEmpty()) {
            println("$name wears an equipment now!")
        }
    }

    fun playPhase() {
        if (skipPlayPhase) {
            skipPlayPhase = false
            return
        }

        if (hasAttackCard()) {
            val attackCard = cards.find { it is AttackCard } as AttackCard
            val opponents = GeneralManager.getOpponents(this)
            val target = strategy?.whomToAttack(this, opponents)
            if (target != null) {
                val isFireAttack = (0..1).random() == 1 // Randomly decide between normal and Fire Attack
                val useDuelCard = (0..1).random() == 1
                if (isFireAttack) {
                    attackCard.isFireAttack = true
                    println("$name spends a card to perform a Fire Attack on ${target.name}")
                    attackCard.execute(target)()
                } else {
                    attackCard.isFireAttack = false
                    println("$name spends a card to attack ${target.name}")
                    val result = attackCard.execute(target)()
//                    val result = target.beingAttacked(this)
                    // Use Wine card to boost attack
                    val wineCards = cards.filterIsInstance<WineCard>() // Filter for WineCard instances
                    if (wineCards.isNotEmpty()) {
                        val wineCard = wineCards.first() // Get the first WineCard from the list
                        wineCard.revive(this)
                        wineCard.boostAttack(target, result)
                        cards.remove(wineCard)
                        CardManager.discard(wineCard)
                    }

                }
                cards.remove(attackCard)
                CardManager.discard(attackCard)

//                if (useDuelCard) {
//                    val duelCard = Duel(this)
//                    println("[DUEL] $name is challenging ${target.name}")
//                    duelCard.useAttackCards(target)
//                }
            }
        } else if (currentHP <= 0) {
            // Use Wine card to revive if on the brink of death
            val wineCards = cards.filterIsInstance<WineCard>() // Filter for WineCard instances
            if (wineCards.isNotEmpty()) {
                val wineCard = wineCards.first() // Get the first WineCard from the list
                wineCard.revive(this)
                cards.remove(wineCard)
                CardManager.discard(wineCard)
            } else {
                println("$name doesn't have a Wine card to revive!")
            }

        } else {
            println("$name doesn't have an attack card.")
        }
        println("$name has ${cards.size} card(s), current HP is $currentHP.")
    }

    open fun discardPhase() {
        println("$name has ${cards.size} card(s), current HP is $currentHP.")
        var discarded = 0

        // If there are more cards than currentHP, discard the excess cards
        while (cards.size > currentHP && cards.size > 0) {
            val cardToDiscard = cards.random() // Select a random card to discard
            cards.remove(cardToDiscard)
            CardManager.discard(cardToDiscard)
        }

        println("$name discards $discarded card(s), now has ${cards.size} card(s).")
        if (this is DiaoChen) {
            CardManager.draw(this, 1)
            println("[Beauty Outshining the Moon] $name now has ${cards.size} card(s).")
        }
    }

    fun isKilled(source: General?) {
        if (currentHP <= 0) {
            println("$name is killed.")
            while (cards.isNotEmpty()) {
                val card = cards[0]
                cards.remove(card)
                CardManager.discard(card)
            }
            GeneralManager.list.remove(this)
            if (source?.strategy is RebelStrategy) {
                println("Killer is a Rebel, so the player gets 3 cards.")
                CardManager.draw(source, 3)
            } else if (this.strategy is LoyalistStrategy && source?.strategy is LordStrategy) {
                println("Loyalist just killed a Lord, so he loses all his cards.")
                while (source.cards.isNotEmpty()) {
                    val card = source.cards[0]
                    source.cards.remove(card)
                    CardManager.discard(card)
                }
            }
            println("Is the game over?")
            if (GeneralManager.isGameOver()) {
                exitProcess(0)
            }
        }
    }
    fun finalPhase() {

    }
}

