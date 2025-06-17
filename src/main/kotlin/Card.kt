import org.example.EightTrigrams
import org.example.Equipment
import org.example.General
import org.example.Player

typealias CommandGenerator = (General) -> () -> Boolean?

abstract class Card(val suit: String, val number: String) {

}


object CardManager {
    val deck: MutableList<Card> = mutableListOf()
    val discarded: MutableList<Card> = mutableListOf()

    fun createDeck() {
        val suits = arrayOf("Heart", "Diamond", "Spade", "Club")

        for (i in arrayOf("8", "9", "10", "J")) {
            for (suit in suits) {
                deck.add(AttackCard(suit, i, null))
            }
        }
        for (suit in suits) {
            if (suit != "Heart")
                deck.add(DuelCard(suit, "A", null))
        }
        deck.add(RattanArmor("Heart", "7", null))
        deck.add(EightTrigrams("Spade", "5"))
        deck.shuffle()
    }

    fun discard(card: Card) {
        deck.remove(card)
        if (card is AttackCard) {
            card.isFireAttack = false
            card.source = null
            card.attackRange = 1
        }
        if (card is DuelCard)
            card.source = null
        if (card is RattanArmor) {
            card.general = null
        }
        discarded.add(card)
    }

    fun draw(general: General, n: Int): List<Card> {
        if (deck.size <= 1) {
            println("Not enough cards left in the deck. Shuffling the discarded cards back into the deck...")
            deck.addAll(discarded)
            discarded.clear()
            deck.shuffle()
        }
        val cards = deck.take(n)
        for (card in cards) {
            if (card is AttackCard)
                card.source = general
            if (card is DuelCard)
                card.source = general
            if (card is RattanArmor)
                card.general = general
            if (card is Equipment)
                general.equipmentCard = card
        }
        deck.removeAll(cards)
        general.cards.addAll(cards.filter { it !is Equipment })
        return cards
    }
}