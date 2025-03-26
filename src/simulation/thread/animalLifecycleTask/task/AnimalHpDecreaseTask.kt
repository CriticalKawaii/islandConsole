package simulation.thread.animalLifecycleTask.task

import field.IslandField
import field.Location
import lifeform.animal.Animal
import simulation.IslandSimulation
import java.util.concurrent.CountDownLatch

/**
 * Задача для уменьшения здоровья животных на острове
 */
class AnimalHpDecreaseTask
/**
 * Конструктор класса
 * @param latch Счетчик CountDownLatch для синхронизации потоков
 */
    (private val latch: CountDownLatch) : Runnable {
    private var percentOfHpToDecrease = 20.0
    var animalsDiedByHungry: Int = 0
        private set

    override fun run() {
        animalsDiedByHungry = 0
        val minutesElapsed = IslandSimulation.getInstance().timeNow / 60
        val currentPercentOfHpToDecrease = if (minutesElapsed >= 3) 40.0 else 20.0

        //println("HP decrease set to $currentPercentOfHpToDecrease% after $minutesElapsed minutes.")

        val animals = IslandField.getAllAnimals()
        for (animal in animals) {
            val hpToDecrease = animal.maxHp * currentPercentOfHpToDecrease / 100.0
            if (animal.hp - hpToDecrease > 0) {
                animal.hp -= hpToDecrease
            } else {
                val location = IslandField.getLocation(animal.row, animal.column)
                IslandField.removeAnimal(animal, location.row, location.column)
                animalsDiedByHungry++
            }
        }
        latch.countDown()
    }

}