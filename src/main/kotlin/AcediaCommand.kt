package org.example

class AcediaCommand(private val player: Player) : Command {
    override fun execute(target: General?) {
        println("${player.name} judging the Acedia card.")
        val judgement = (0..3).random() == 0
        if (!judgement) {
            println("${player.name} can't dodge the Acedia card. Skipping one round of Play Phase.")
            player.skipPlayPhase = true
        } else {
            println("${player.name} dodged the Acedia card.")
        }
    }
}