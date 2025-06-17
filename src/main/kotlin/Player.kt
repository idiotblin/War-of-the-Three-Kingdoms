package org.example

import Card

interface Player {
    val name: String
    val maxHP: Int
    var currentHP: Int
    var skipPlayPhase: Boolean
    var strategy: Strategy?
    val cards: MutableList<Card>
    var distance: HashMap<Player, Int>
    var timeDelayCards: MutableList<AcediaCommand>

}