package org.example

import Card
import CommandGenerator

class EightTrigrams(suit: String, number: String) : Equipment (suit, number){

    override fun use(): Boolean {
        println("Triggering the Eight Trigrams")
        val judgement = (0..1).random() == 1 // if the card drawn is red, then dodged successfully
        println("Judgement is $judgement")
        return judgement
    }
}