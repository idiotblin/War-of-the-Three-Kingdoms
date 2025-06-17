package org.example

class NonLordFactory(private val participants: Int, private val start: WeiGeneral? = null) : GeneralFactory() {
    private val nonLords = mutableListOf(
        "SimaYi", "ZhenJi", "GuanYu", "ZhengFei", "XuChu", "DiaoChen", "ZhouYu", "XiahouDun", "LvBu", "ZhugeLiang"
    )
    private val roles = mutableListOf<String>()

    init {
        when (participants) {
            4 -> roles.addAll(listOf("Loyalist", "Rebel", "Spy"))
            5 -> roles.addAll(listOf("Loyalist", "Loyalist", "Rebel", "Spy"))
            6 -> roles.addAll(listOf("Loyalist", "Loyalist", "Loyalist", "Rebel", "Rebel", "Spy"))
            7 -> roles.addAll(listOf("Loyalist", "Loyalist", "Rebel", "Rebel", "Rebel", "Spy"))
            8 -> roles.addAll(listOf("Loyalist", "Loyalist", "Rebel", "Rebel", "Rebel", "Rebel", "Spy"))
            9 -> roles.addAll(listOf("Loyalist", "Loyalist", "Loyalist", "Rebel", "Rebel", "Rebel", "Rebel", "Spy"))
            10 -> roles.addAll(
                listOf(
                    "Loyalist",
                    "Loyalist",
                    "Loyalist",
                    "Rebel",
                    "Rebel",
                    "Rebel",
                    "Rebel",
                    "Spy",
                    "Spy"
                )
            )

            else -> throw IllegalArgumentException("Unsupported number of participants: $participants")
        }
    }

    override fun createRandomGeneral(): General {
        if (roles.isEmpty()) {
            throw IllegalStateException("All non-lord roles have been assigned.")
        }
        val randomGeneralName = nonLords.random()
        nonLords.remove(randomGeneralName)
        val randomRole = roles.random()
        roles.remove(randomRole)

        val general = when (randomGeneralName) {
            "SimaYi" -> SimaYi()
            "ZhenJi" -> ZhenJi()
            "GuanYu" -> GuanYu()
            "ZhengFei" -> ZhengFei()
            "XuChu" -> XuChu()
            "DiaoChen" -> DiaoChen()
            "ZhouYu" -> ZhouYu()
            "XiahouDun" -> XiahouDun()
            "LvBu" -> LvBu()
            "ZhugeLiang" -> ZhugeLiang()
            else -> throw IllegalArgumentException("Unknown role: $randomRole")
        }
        when (randomRole) {
            "Loyalist" -> general.strategy = LoyalistStrategy()
            "Rebel" -> general.strategy = RebelStrategy()
            "Spy" -> {
                val spyStrategy = SpyStrategy(general.name)
                general.strategy = spyStrategy
                (GeneralManager.get(0).strategy as LordStrategy).registerObserver(spyStrategy)
            }
        }
        general.currentHP = general.maxHP
        println(general.name + ", a $randomRole, has " + general.currentHP + " health point(s).")
        if (randomRole == "Spy")
            println(general.name + " is observing lord.")
        if (general is WeiGeneral) {
            if (start != null) {
                var current = start
                while (current?.next != null) {
                    current = current.next
                }
                current?.next = general
                println("${general.name} added to the Wei chain.")
            }
        }
        return general
    }
}