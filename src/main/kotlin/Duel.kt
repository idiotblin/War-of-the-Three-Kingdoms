import org.example.General

class Duel(private val source: General) {
    fun useAttackCards(target: General){
        var keepTrackTarget = target.cards.size

        while(keepTrackTarget != 0) {
            if (target.hasAttackCard()) {
                println("${target.name} is using an attack card")
                if (source.hasAttackCard()) {
                    println("${source.name} is using an attack card")
//                    source.numOfCards -= 1
                } else {
                    println("${source.name} does not have an attack card")
                    source.currentHP -= 1
                    println("${source.name} current HP is now " + "${source.currentHP}")
                    println("[DUEL ENDED] ${target.name} won.")
                    break
                }
            } else {
                println("${target.name} does not have an attack card")
                target.currentHP -= 1
                println("${target.name} current HP is now " + "${target.currentHP}")
                println("[DUEL ENDED] ${source.name} won.")
                break
            }
        }
    }
}