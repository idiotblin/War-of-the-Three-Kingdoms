package org.example

import CardManager

class XuChu : WeiGeneral("Xu Chu") {
    override val maxHP: Int = 4
}

class SimaYi : WeiGeneral("Sima Yi") {
    override val maxHP: Int = 5
}

class XiahouDun : WeiGeneral("Xiahou Dun") {
    override val maxHP: Int = 5
}

class ZhengFei : General("Zheng Fei") {
    override val maxHP: Int = 5
}

class ZhenJi : WeiGeneral("Zhen Ji") {
    override val maxHP: Int = 5
}

class GuanYu : General("Guan Yu"){
    override val maxHP = 4
}


class ZhouYu : General("Zhou Yu") {
    override val maxHP: Int = 3

    override fun drawPhase() {
        CardManager.draw(this, 3)
    }
}

class DiaoChen : General("Diao Chen") {
    override val maxHP: Int = 3
}

class LvBu : WeiGeneral("Lv Bu") {
    override val maxHP: Int = 5
}

class ZhugeLiang : General("Zhuge Liang") {
    override val maxHP: Int = 5
}
