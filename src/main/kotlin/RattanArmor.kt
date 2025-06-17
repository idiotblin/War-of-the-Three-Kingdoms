import org.example.Equipment
import org.example.General
import org.example.Player

/**
 * Represents the Rattan Armor equipment.
 * Rattan Armor reduces damage from normal attacks to 1 and ignores dodge attempts for fire attacks,
 * dealing 2 damage directly.
 *
 * @param player The player who equips the Rattan Armor.
 */
class RattanArmor(suit: String, number: String, var general: General?) : Equipment (suit, number) {
    override fun use(): Boolean? {
        println("${general?.name} is wearing Rattan Armor.")
        general?.currentHP?.minus(2) // Fire Attack ignores Rattan Armor and deals 2 damage
        println("${general?.name} takes 2 damage from Fire Attack, current HP is ${general?.currentHP}")
        return true
    }
}