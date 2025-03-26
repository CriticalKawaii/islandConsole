package field

import lifeform.animal.Animal
import lifeform.plant.Plant

object IslandField {
    private lateinit var locations: Array<Array<Location>> // Двумерный массив состоящий из локаций(ячеек)
    val numRows = 10 // default
    val numColumns = 4 // default

    fun initializeLocations(rows: Int = numRows, columns: Int = numColumns) {
        locations = Array(rows) { row -> Array(columns) { col -> Location(row, col) } }
    }

    @Synchronized
    fun getLocation(row: Int, column: Int): Location = locations[row][column]

    fun addAnimal(animal: Animal, row: Int, column: Int) {
        getLocation(row, column).addAnimal(animal)
    }

    fun removeAnimal(animal: Animal, row: Int, column: Int) {
        getLocation(row, column).removeAnimal(animal)
    }

    fun addPlant(plant: Plant, row: Int, column: Int) {
        getLocation(row, column).addPlant(plant)
    }

    fun removePlant(plant: Plant, row: Int, column: Int) {
        getLocation(row, column).removePlant(plant)
    }

    @Synchronized
    fun getAllAnimals(): List<Animal> = locations.flatMap { row -> row.flatMap { it.animals } }

    fun getAllPlants(): List<Plant> = locations.flatMap { row -> row.flatMap { it.plants } }
}
