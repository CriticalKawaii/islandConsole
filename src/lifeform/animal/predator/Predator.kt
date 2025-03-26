package lifeform.animal.predator

import lifeform.animal.Animal

abstract class Predator(
    weight: Double,
    step: Int,
    maxHp: Double,
    maxPopulation: Int,
    name: String
) : Animal(weight, maxPopulation,name, step, maxHp) {

    abstract override fun getChanceToEat(foodName: String): Double
}

class Wolf : Predator(50.0, 3, 8.0, 30, "Wolf") {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Horse" -> 0.1
        "Deer" -> 0.15
        "Rabbit" -> 0.6
        "Mouse" -> 0.8
        "Goat" -> 0.6
        "Sheep" -> 0.7
        "Boar" -> 0.15
        "Buffalo" -> 0.1
        "Duck" -> 0.4
        else -> 0.0
    }
}

class Boa : Predator(15.0, 1, 3.0, 30, "Boa") {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Fox" -> 0.15
        "Rabbit" -> 0.2
        "Mouse" -> 0.4
        "Duck" -> 0.1
        else -> 0.0
    }
}

class Fox : Predator(8.0, 2, 2.0, 30, "Fox") {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Rabbit" -> 0.7
        "Mouse" -> 0.9
        "Duck" -> 0.6
        "Caterpillar" -> 0.4
        else -> 0.0
    }
}

class Bear : Predator(500.0, 2, 80.0, 5, "Bear") {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Boa" -> 0.8
        "Horse" -> 0.4
        "Deer" -> 0.8
        "Rabbit" -> 0.8
        "Mouse" -> 0.9
        "Goat" -> 0.7
        "Sheep" -> 0.7
        "Boar" -> 0.5
        "Buffalo" -> 0.2
        "Duck" -> 0.1
        else -> 0.0
    }
}

class Eagle : Predator(6.0, 3, 1.0, 20, "Eagle") {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Fox" -> 0.1
        "Rabbit" -> 0.9
        "Mouse" -> 0.9
        "Duck" -> 0.8
        else -> 0.0
    }
}