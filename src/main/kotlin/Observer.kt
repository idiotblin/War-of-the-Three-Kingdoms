package org.example

interface Observer {
    fun update(event: String)
}

interface Subject {
    fun registerObserver(observer: Observer)
    fun unregisterObserver(observer: Observer)
    fun notifyObservers(event: String)
}