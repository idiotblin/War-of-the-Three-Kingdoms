import org.example.Player

/**
 * Represents the Wine card, which has dual actions:
 * 1. Boosts attack damage by 1 when used with an ATTACK card.
 * 2. Revives the player by 1 HP when they are on the brink of death (0 or negative HP).
 */
class WineCard(suit: String, number: String) : Card(suit, number){
    /**
     * Boosts the damage of an attack by 1.
     *
     * @param target The target player being attacked.
     */
    fun boostAttack(target: Player, dodgeStatus: Boolean?) {
        println("Wine card boosts the attack! Inflicting 1 additional damage.")
        if (dodgeStatus == false) {
            target.currentHP -= 1 // Additional damage from Wine card
            println("${target.name} takes 1 additional damage from the Wine card. Current HP: ${target.currentHP}")
        } else {
            println("${target.name} dodged the boosted attack!")
        }
    }

    /**
     * Revives the player by 1 HP if they are on the brink of death.
     *
     * @param player The player using the Wine card.
     */
    fun revive(player: Player) {
        if (player.currentHP <= 0) {
            player.currentHP += 1
            println("${player.name} uses a Wine card to revive! Current HP: ${player.currentHP}")
        } else {
            println("Wine card cannot be used to revive ${player.name} because they are not on the brink of death.")
        }
    }
}