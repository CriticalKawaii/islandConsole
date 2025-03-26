package field

import lifeform.LifeForm
import lifeform.animal.Animal
import lifeform.plant.Plant

class Location(val row: Int, val column: Int) {
    val animals: MutableList<Animal> = mutableListOf()
    val plants: MutableList<Plant> = mutableListOf()

    fun addAnimal(animal: Animal) {
        animal.row = row
        animal.column = column
        animals.add(animal)
    }

    fun removeAnimal(animal: Animal) {
        animals.remove(animal)
    }

    fun addPlant(plant: Plant) {
        plant.row = row
        plant.column = column
        plants.add(plant)
    }

    fun removePlant(plant: Plant) {
        plants.remove(plant)
    }



    fun getLifeForms(): List<LifeForm> = animals + plants
}
