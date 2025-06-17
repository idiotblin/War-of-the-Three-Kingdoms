package org.example

abstract class Strategy {
    abstract fun whomToAttack(player: General, opponents: List<General>): General?
}

open class LordStrategy : Strategy(), Subject {
    private val observers = mutableListOf<Observer>()

    override fun whomToAttack(player: General, opponents: List<General>): General? {
        return opponents.firstOrNull { it.strategy is RebelStrategy } ?: run {
            null
        }
    }


    override fun registerObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun unregisterObserver(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObservers(event: String) {
        for (observer in observers) {
            observer.update(event)
        }
    }
}

class SpyStrategy(val name: String) : Strategy(), Observer {
    var riskLevel = 50.0

    override fun update(event: String) {
        when (event) {
            "dodge" -> riskLevel *= 0.5
            "no_dodge" -> riskLevel *= 1.5
        }
        println("$name on Lord's Risk Level: $riskLevel")
    }

    override fun whomToAttack(player: General, opponents: List<General>): General? {
        return opponents.firstOrNull { it.strategy is RebelStrategy } ?: run {
            null
        }
    }

}

class LoyalistStrategy : Strategy() {
    override fun whomToAttack(player: General, opponents: List<General>): General? {
        return opponents.firstOrNull { it.strategy is RebelStrategy } ?: run {
            null
        }
    }

}

class RebelStrategy : Strategy() {
    override fun whomToAttack(player: General, opponents: List<General>): General? {
        return opponents.firstOrNull { it.strategy is LordStrategy } ?: run {
            null
        }
    }

}

class LiuBeiStrategy : LordStrategy() {
    var state: State = UnhealthyState()

     fun playNextCard(player: Player) {
        if (player is LiuBei) {
            state.playNextCard(player)
        }
    }
}