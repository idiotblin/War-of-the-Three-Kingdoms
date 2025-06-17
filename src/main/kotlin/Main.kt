import org.example.GeneralManager
import org.example.LordFactory
import org.example.NonLordFactory
import org.example.WeiGeneral

fun main() {
    GeneralManager.createGenerals(1, 3);
    val generalCount = GeneralManager.getGeneralCount()
    println("Total number of players: $generalCount \n")

    // Equip a random player with Rattan Armor
    val randomPlayerIndex = (0 until GeneralManager.getGeneralCount()).random()
    val randomPlayer = GeneralManager.get(randomPlayerIndex)
//    GeneralManager.equipRattanArmor(randomPlayer)

    GeneralManager.gameStart()
}