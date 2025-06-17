package org.example

class LordFactory: GeneralFactory() {
    private val lords = listOf("LiuBei", "SunQuan", "CaoCao")

    override fun createRandomGeneral(): General {
        val lord = when (val randomLord = lords.random()) {
            "LiuBei" -> LiuBei()
            "SunQuan" -> SunQuan()
            "CaoCao" -> CaoCao()
            else -> throw IllegalArgumentException("Unknown lord: $randomLord")
        }
        if (lord.name == "Liu Bei") {
            lord.currentHP = 1
            lord.strategy = LiuBeiStrategy()
        } else {
            lord.currentHP = lord.maxHP
            lord.strategy = LordStrategy()
        }
        println(lord.name + ", a Lord, has " + lord.currentHP + " health point(s).")
        return lord
    }
}