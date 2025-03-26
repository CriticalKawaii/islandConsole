package simulation.thread.animalLifecycleTask.task

import field.IslandField
import field.Location
import lifeform.LifeForm
import lifeform.animal.Animal
import lifeform.plant.Plant
import simulation.IslandSimulation
import simulation.thread.StatisticsTask
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

/**
 * Задача для питания животных на острове
 */
class AnimalEatTask(private val latch: CountDownLatch) : Runnable {
    var animalsEaten: Int = 0
        private set

    override fun run() {
        animalsEaten = 0
        val animals: List<Animal> = IslandField.getAllAnimals()
        val lifeFormsEaten: MutableList<LifeForm> = ArrayList()
        val countAnimalsStart = animals.size

        if (countAnimalsStart > 0 && animals.filter { it.name != "Caterpillar" }.isNotEmpty()) {
            for (currentAnimal in animals) {
                if (currentAnimal.maxHp > 0) {
                    val location: Location = IslandField.getLocation(currentAnimal.row, currentAnimal.column)
                    val locationLifeForms = location.getLifeForms()

                    if (locationLifeForms.isNotEmpty()) {
                        for (lifeForm in locationLifeForms) {

                            if (currentAnimal.getChanceToEat(lifeForm.name) > 0 && !lifeFormsEaten.contains(lifeForm)) {
                                val isEaten = currentAnimal.eat(lifeForm)

                                if (isEaten) {
                                    if (lifeForm is Animal) {
                                        // Удаляем съеденное животное
                                        if (location.animals.contains(lifeForm)) {
                                            IslandField.removeAnimal(lifeForm, location.row, location.column)
                                        }
                                        lifeFormsEaten.add(lifeForm)
                                        animalsEaten++
                                    }
                                    else {
                                        val plant = lifeForm as Plant
                                        if (location.plants.contains(plant)) {
                                            IslandField.removePlant(plant, location.row, location.column)
                                        }
                                    }
                                }
                                // После успешной попытки питания прекращаем дальнейший поиск пищи для данного животного
                                break
                            }
                        }
                    }
                }
                // НЕ удаляем животное из локации, если оно не было съедено или не умерло
            }
        } else if (countAnimalsStart == 0) {
            System.out.printf("ВЫ ПРОИГРАЛИ! ВСЕ ЖИВОТНЫЕ УМЕРЛИ НА %d ДЕНЬ!", StatisticsTask.currentDay)
            IslandSimulation.getInstance().executor?.shutdown()
            exitProcess(0)
        } else {
            System.out.printf(
                "ПОБЕДИЛИ ГУСЕНИЦЫ! В ЖИВЫХ ОСТАЛИСЬ ТОЛЬКО ОНИ НА %d ДЕНЬ!",
                StatisticsTask.currentDay
            ) // так как они бессмертные и не требуют пищи
            IslandSimulation.getInstance().executor?.shutdown()
            exitProcess(0)
        }
        latch.countDown()
    }
}


/*
package simulation.thread.animalLifecycleTask.task

import field.IslandField
import field.Location
import lifeform.LifeForm
import lifeform.animal.Animal
import lifeform.plant.Plant
import simulation.IslandSimulation
import simulation.thread.StatisticsTask
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

*/
/**
 * Задача для питания животных на острове
 *//*

class AnimalEatTask
*/
/**
 * Конструктор класса
 * @param latch Счетчик CountDownLatch для синхронизации потоков
 *//*
(private val latch: CountDownLatch) : Runnable {
    var animalsEaten: Int = 0
        private set

    override fun run() {
        animalsEaten = 0
        val animals: List<Animal> = IslandField.getAllAnimals()
        val lifeFormsEaten: MutableList<LifeForm> = ArrayList()
        val countAnimalsStart = animals.size

        if (countAnimalsStart > 0 && animals.stream().filter { c: Animal -> !(c.name == "Caterpillar") }
                .toList().size > 0) {
            val animalsToRemove: MutableList<Animal> = mutableListOf()

            for (currentAnimal in animals) {
                if (currentAnimal.maxHp > 0) {
                    val location: Location = IslandField.getLocation(currentAnimal.row, currentAnimal.column)
                    val locationLifeForms = location.getLifeForms()

                    if (locationLifeForms.isNotEmpty()) {
                        for (lifeForm in locationLifeForms) {
                            if (currentAnimal.getChanceToEat(lifeForm.name) > 0 && !lifeFormsEaten.contains(lifeForm)) {
                                val isEaten = currentAnimal.eat(lifeForm)

                                if (isEaten) {
                                    if (lifeForm is Animal) {
                                        if (location.animals.contains(lifeForm)) {
                                            IslandField.removeAnimal(lifeForm, location.row, location.column)
                                        }
                                        lifeFormsEaten.add(lifeForm)
                                        animalsEaten++
                                    } else {
                                        val plant = lifeForm as Plant
                                        if (location.plants.isNotEmpty()) {
                                            IslandField.removePlant(plant, location.row, location.column)
                                        }
                                    }
                                }
                                break
                            }
                        }
                    }
                }
                // Add the animal to be removed after the loop
                animalsToRemove.add(currentAnimal)
            }

            // Remove the animals outside of the loop to avoid modifying the collection during iteration
            for (animal in animalsToRemove) {
                IslandField.removeAnimal(animal, animal.row, animal.column)
            }
        } else if (countAnimalsStart == 0) {
            System.out.printf("ВЫ ПРОИГРАЛИ! ВСЕ ЖИВОТНЫЕ УМЕРЛИ НА %d ДЕНЬ!", StatisticsTask.currentDay)
            IslandSimulation.getInstance().executor?.shutdown()
            exitProcess(0)
        } else {
            System.out.printf(
                "ПОБЕДИЛИ ГУСЕНИЦЫ! В ЖИВЫХ ОСТАЛИСЬ ТОЛЬКО ОНИ НА %d ДЕНЬ!",
                StatisticsTask.currentDay
            ) // так как они бесссмертные и не требуют пищи
            IslandSimulation.getInstance().executor?.shutdown()
            exitProcess(0)
        }
        latch.countDown()
    }
}
package simulation.thread.animalLifecycleTask.task

import field.IslandField
import field.Location
import lifeform.LifeForm
import lifeform.animal.Animal
import lifeform.plant.Plant
import simulation.IslandSimulation
import simulation.thread.StatisticsTask
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

/**
 * Задача для питания животных на острове
 */
class AnimalEatTask(private val latch: CountDownLatch) : Runnable {
    public var animalsEaten: Int = 0

    override fun run() {
        animalsEaten = 0
        val animals = IslandField.getAllAnimals()
        val lifeFormsEaten = mutableListOf<LifeForm>()
        val countAnimalsStart = animals.size

        if (countAnimalsStart > 0 && animals.any { c: Animal -> c.name != "Caterpillar" }) {
            val animalsToRemove = mutableListOf<Animal>()

            for (animal in animals) {
                if (animal.maxHp > 0) {
                    val location = IslandField.getLocation(animal.row, animal.column)
                    val locationLifeForms = location.getLifeForms()

                    if (locationLifeForms.isNotEmpty()) {
                        for (lifeForm in locationLifeForms) {
                            if (animal.getChanceToEat(lifeForm.name) > 0 && lifeForm !in lifeFormsEaten) {
                                val isEaten = animal.eat(lifeForm)

                                if (isEaten) {
                                    when (lifeForm) {
                                        is Animal -> {
                                            if (location.animals.contains(lifeForm)) {
                                                IslandField.removeAnimal(lifeForm, location.row, location.column)
                                            }
                                            lifeFormsEaten.add(lifeForm)
                                            animalsEaten++
                                        }
                                        is Plant -> {
                                            if (location.plants.isNotEmpty()) {
                                                IslandField.removePlant(lifeForm, location.row, location.column)
                                            }
                                        }
                                    }
                                }
                                break
                            }
                        }
                    }
                }
                animalsToRemove.add(animal) // Mark for removal
            }
            for (animal in animalsToRemove) {
                IslandField.removeAnimal(animal, animal.row, animal.column)
            }

        } else if (countAnimalsStart == 0) {
            println("ВЫ ПРОИГРАЛИ! ВСЕ ЖИВОТНЫЕ УМЕРЛИ НА ${StatisticsTask.currentDay} ДЕНЬ!")
            IslandSimulation.getInstance().executor?.shutdown()
            exitProcess(0)
        } else {
            println("ПОБЕДИЛИ ГУСЕНИЦЫ! В ЖИВЫХ ОСТАЛИСЬ ТОЛЬКО ОНИ НА ${StatisticsTask.currentDay} ДЕНЬ!")
            IslandSimulation.getInstance().executor?.shutdown()
            exitProcess(0)
        }
        latch.countDown()
    }
}

*/
