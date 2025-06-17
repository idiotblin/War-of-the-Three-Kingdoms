import org.example.General

open class AttackCard(suit: String, number: String, var source: General?) : Card(suit, number) {
    open var attackRange = 1
    open var isFireAttack = false

    val execute: CommandGenerator = { target ->
        {
            if (source?.distance?.get(target)!! <= attackRange) {
                println("Attack is within the range")
                if (isFireAttack){
                    source?.let { target.beingFireAttacked() }
                    true
                }
                else {
                    val res = source?.let { target.beingAttacked(it) } ?: false
                    !res

                }
            } else {
                println("Attack is not in range")
                false
            }
        }
    }

}

