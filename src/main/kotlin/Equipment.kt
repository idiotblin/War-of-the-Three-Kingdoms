package org.example

import Card

abstract class Equipment(suit: String, number: String) : Card(suit, number) {
    abstract fun use(): Boolean?
}