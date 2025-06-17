package org.example

import CardManager
import RattanArmor

object GeneralManager {
    val list: MutableList<General> = mutableListOf()

    fun addGeneral(general: General) {
        list.add(general)
    }

    fun removeGeneral(general: General) {
        list.remove(general)
        println("General ${general.name} removed from the list.")
    }

    fun getGeneralCount(): Int {
        return list.size
    }

    fun get(ind: Int): Player {
        return list[ind]
    }

    fun createGenerals(lords: Int, nonLords: Int) {
        val lordFactory = LordFactory()
        val lord = lordFactory.createRandomGeneral()
        addGeneral(lord)

        val nonLordFactory: NonLordFactory = if (lord.name == "Cao Cao") {
            NonLordFactory(4, lord as WeiGeneral)
        } else {
            NonLordFactory(4)
        }

        for (i in 1..nonLords) {
            val nonLord = nonLordFactory.createRandomGeneral()
            addGeneral(nonLord)
        }
    }

    fun getOpponents(player: General): List<General> {
        return list.filter { it != player }
    }

    fun gameStart() {
        CardManager.createDeck()

        placeAcediaCard(list[3])
        while (!isGameOver()) {
            for (i in list) {
                i.takeTurn()
            }
            println()
        }
        println()
//        list.find { it.name == "Cao Cao" }?.beingAttacked(null)
    }

    fun placeAcediaCard(player: Player) {
        println("${player.name} being placed the Acedia card.")
        val acediaCommand = AcediaCommand(player)
        player.timeDelayCards.add(acediaCommand)
    }

    fun isGameOver(): Boolean {
        var lordsLeft = 0
        var spyLeft = 0
        var loyalistLeft = 0
        var rebelLeft = 0
        for (i in list) {
            if (i.strategy is LordStrategy)
                lordsLeft++
            if (i.strategy is SpyStrategy)
                spyLeft++
            if (i.strategy is RebelStrategy)
                rebelLeft++
            if (i.strategy is LoyalistStrategy)
                loyalistLeft++
        }
        // The Lord is dead: The Spy will win if he/she is the only survivor. Otherwise, the Rebel wins.
        if (lordsLeft == 0) {
            if (spyLeft == list.size && spyLeft > 0) {
                println("Spy wins!")
                return true
            } else {
                println("Rebel Wins!")
                return true
            }
        }
        // All Rebels and Spies are dead: The Lord and the Loyalists win.
        if (rebelLeft == 0 && spyLeft == 0) {
            println("Lord and Loyalists win!")
            return true
        }
        println("Game is not over yet!")
        // If none of the above conditions are met, the game is not over.
        return false
    }
}