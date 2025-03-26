package simulation

import field.IslandField
import field.Location
import lifeform.animal.herbivore.*
import lifeform.animal.predator.*
import lifeform.plant.Plant
import simulation.thread.PlantGrowthTask
import simulation.thread.StatisticsTask
import simulation.thread.animalLifecycleTask.AnimalLifecycleTask
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

/**
 * Класс, моделирующий островную экосистему
 */
class IslandSimulation private constructor() {
    private val startTime: Long = System.currentTimeMillis()
    private val countHerbivores = 35
    private val countPlants = 40
    private val countPredators = 20
    private var executorService: ScheduledExecutorService? = null

    companion object {
        @Volatile
        private var instance: IslandSimulation? = null

        /**
         * Получить экземпляр класса IslandSimulation (Singleton)
         *
         * @return instance Экземпляр класса IslandSimulation
         */
        fun getInstance(): IslandSimulation {
            return instance ?: synchronized(this) {
                instance ?: IslandSimulation().also { instance = it }
            }
        }
    }

    /**
     * Создать модель острова с заданным количеством травоядных, хищников и растений
     *
     * @param countHerbivores Количество травоядных
     * @param countPredators  Количество хищников
     * @param countPlants     Количество растений
     */
    fun createIslandModel(countHerbivores: Int, countPredators: Int, countPlants: Int) {
        placeHerbivores(countHerbivores)
        placePredators(countPredators)
        placePlants(countPlants)
        runIslandModel()
    }

    /**
     * Создать модель острова с количеством травоядных, хищников и растений, заданным по умолчанию
     */
    fun createIslandModel() {
        placeHerbivores(countHerbivores)
        placePredators(countPredators)
        placePlants(countPlants)
        runIslandModel()
    }

    /**
     * Запустить модель острова
     */
    private fun runIslandModel() {
        executorService = Executors.newScheduledThreadPool(3)

        val animalLifecycleTask = AnimalLifecycleTask()
        val plantGrowthTask = PlantGrowthTask()
        val statisticsTask = StatisticsTask(animalLifecycleTask.animalEatTask, animalLifecycleTask.animalHpDecreaseTask, animalLifecycleTask.animalMultiplyTask)

        executorService?.scheduleAtFixedRate(animalLifecycleTask, 1, 8, TimeUnit.SECONDS)
        executorService?.scheduleAtFixedRate(plantGrowthTask, 40, 30, TimeUnit.SECONDS)
        executorService?.scheduleAtFixedRate(statisticsTask, 0, 8, TimeUnit.SECONDS)
    }

    /**
     * Создать список травоядных с заданным количеством
     *
     * @param countHerbivores Количество травоядных
     * @return herbivores Список травоядных
     */
    private fun createHerbivores(countHerbivores: Int): List<Herbivore> {
        val herbivores = mutableListOf(
            Buffalo(),
            Caterpillar(),
            Deer(),
            Duck(),
            Goat(),
            Horse(),
            Mouse(),
            Rabbit(),
            Sheep(),
            Boar()
        )

        val random = Random()
        val remainingCount = countHerbivores - herbivores.size

        for (i in 0..<remainingCount) {
            // Генерируем случайный индекс для выбора вида животного
            val randomIndex = random.nextInt(herbivores.size)
            val randomHerbivore = herbivores[randomIndex]
            try {
                // Создаем экземпляр животного через рефлексию
                val newHerbivore = randomHerbivore.javaClass.getDeclaredConstructor().newInstance() as Herbivore
                herbivores.add(newHerbivore)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return herbivores
    }

    /**
     * Создать список хищников с заданным количеством
     *
     * @param countPredators Количество хищников
     * @return predators Список хищников
     */
    private fun createPredators(countPredators: Int): List<Predator> {
        val predators = mutableListOf(
            Bear(),
            Eagle(),
            Fox(),
            Boa(),
            Wolf()
        )

        val random = Random()
        val remainingCount = countPredators - predators.size

        for (i in 0..<remainingCount) {
            // Генерируем случайный индекс для выбора вида животного
            val randomIndex = random.nextInt(predators.size)
            val randomPredator = predators[randomIndex]
            try {
                // Создаем экземпляр животного через рефлексию
                val newPredator = randomPredator.javaClass.getDeclaredConstructor().newInstance() as Predator
                predators.add(newPredator)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return predators
    }

    /**
     * Создать список растений с заданным количеством
     *
     * @param countPlants Количество растений
     * @return plants Список растений
     */
    private fun createPlants(countPlants: Int): List<Plant> {
        return List(countPlants) { Plant() }
    }

    /**
     * Разместить травоядных на острове
     *
     * @param countHerbivores Количество травоядных
     */
    fun placeHerbivores(countHerbivores: Int) {
        val herbivores = createHerbivores(countHerbivores)
        val random = ThreadLocalRandom.current()
        for (herbivore in herbivores) {
            var placed = false
            while (!placed) {
                val row = random.nextInt(IslandField.numRows)
                val column = random.nextInt(IslandField.numColumns)
                val location = IslandField.getLocation(row, column)
                if (location.animals.filter { it.name == herbivore.name }.size <= herbivore.maxPopulation) {
                    IslandField.addAnimal(herbivore, row, column)
                    placed = true
                }
            }
        }
    }

    /**
     * Разместить хищников на острове
     *
     * @param countPredators Количество хищников
     */
    fun placePredators(countPredators: Int) {
        val predators = createPredators(countPredators)
        val random = ThreadLocalRandom.current()
        for (predator in predators) {
            var placed = false
            while (!placed) {
                val row = random.nextInt(IslandField.numRows)
                val column = random.nextInt(IslandField.numColumns)
                val location = IslandField.getLocation(row, column)
                if (location.animals.filter { it.name == predator.name }.size <= predator.maxPopulation) {
                    IslandField.addAnimal(predator, row, column)
                    placed = true
                }
            }
        }
    }

    /**
     * Разместить растения на острове
     *
     * @param countPlants Количество растений
     */
    fun placePlants(countPlants: Int) {
        val plants = createPlants(countPlants)
        val random = ThreadLocalRandom.current()
        for (plant in plants) {
            var placed = false
            while (!placed) {
                val row = random.nextInt(IslandField.numRows)
                val column = random.nextInt(IslandField.numColumns)
                val location = IslandField.getLocation(row, column)
                if (location.plants.size <= plant.maxPopulation) {
                    IslandField.addPlant(plant, row, column)
                    placed = true
                }
            }
        }
    }

    /**
     * Получить текущее время симуляции
     *
     * @return timeNow Текущее время симуляции
     */
    val timeNow: Long
        get() = (System.currentTimeMillis() - startTime) / 500

    val executor: ScheduledExecutorService?
        get() = executorService

    val herbivoresCount: Int
        get() = countHerbivores

    val plantsCount: Int
        get() = countPlants

    val predatorsCount: Int
        get() = countPredators
}
