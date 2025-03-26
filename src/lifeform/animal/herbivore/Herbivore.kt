package lifeform.animal.herbivore

import lifeform.animal.Animal

abstract class Herbivore(
    weight: Double,
    step: Int,
    maxHp: Double,
    maxPopulation: Int,
    name: String
) : Animal(weight, maxPopulation,name, step, maxHp) {

    override fun getChanceToEat(foodName: String): Double {
        return if (foodName == "Plant") 1.0 else 0.0
    }
}

class Horse : Herbivore(400.0, 4, 60.0, 20, "Horse")
class Deer : Herbivore(300.0, 4, 50.0, 20, "Deer")
class Rabbit : Herbivore(2.0, 2, 0.45, 150, "Rabbit")
class Mouse : Herbivore(0.05, 1, 0.01, 500, "Mouse")
class Goat : Herbivore(60.0, 3, 10.0, 140, "Goat")
class Sheep : Herbivore(70.0, 3, 15.0, 140, "Sheep")
class Boar : Herbivore(400.0, 2, 50.0, 50, "Boar")
class Buffalo : Herbivore(700.0, 3, 100.0, 10, "Buffalo")
class Duck : Herbivore(1.0, 4, 0.15, 200, "Duck")
class Caterpillar : Herbivore(0.01, 0, 0.0, 1000, "Caterpillar")