package simulation.thread

import field.IslandField
import simulation.IslandSimulation
import simulation.thread.animalLifecycleTask.task.AnimalEatTask
import simulation.thread.animalLifecycleTask.task.AnimalHpDecreaseTask
import simulation.thread.animalLifecycleTask.task.AnimalMultiplyTask
import kotlin.system.exitProcess

/**
 * Задача для вывода статистики моделирования острова
 */
class StatisticsTask(
    private val animalEatTask: AnimalEatTask,
    private val animalHpDecreaseTask: AnimalHpDecreaseTask,
    private val animalMultiplyTask: AnimalMultiplyTask
) :
    Runnable {
    private var isTimeOver = false
    private var babies = 0
    private var animalsEaten = 0
    private var animalsDiedByHungry = 0
    private var countAnimalsEnd = 0
    private var countPlants = 0

    override fun run() {
        val timeNow: Long = IslandSimulation.getInstance().timeNow
        isTimeOver = checkTime(timeNow)

        babies = animalMultiplyTask.babies
        countAnimalsEnd = IslandField.getAllAnimals().size // кол-во животных на острове
        animalsEaten = animalEatTask.animalsEaten // кол-во животных умерло
        animalsDiedByHungry = animalHpDecreaseTask.animalsDiedByHungry // кол-во животных умерло
        countPlants = IslandField.getAllPlants().size
        printStats()

        if (isTimeOver) {
            IslandSimulation.getInstance().executor?.shutdown()
            exitProcess(0)
        }
    }

    /**
     * Проверить, истекло ли заданное время моделирования
     *
     * @param timeNow Текущее время моделирования
     * @return isTimeOver true, если время истекло, иначе - false
     */
    private fun checkTime(timeNow: Long): Boolean {
        return timeNow / 60 >= 5
    }

    /**
     * Вывести статистику моделирования
     */
    private fun printStats() {
        if (isTimeOver) {
            println("ПОБЕДА!!! ВЫ ПРОДЕРЖАЛИСЬ 5 МИНУТ!")
            println("----------------------------------")
        } else {
            System.out.printf("--- ДЕНЬ %d ---", currentDay)
            currentDay++
            println()
        }
        println()
        println("СТАТИСТИКА ПО ОСТРОВУ")
        println()

        print("Животных на острове: ")
        println(countAnimalsEnd)

        print("Животных умерло от голода: ")
        println(animalsDiedByHungry)

        print("Животных съедено: ")
        println(animalsEaten)

        print("Детенышей родилось: ")
        println(babies)

        print("Растений на острове: ")
        println(countPlants)

        println()
        println("----------------------------------")
        println()
    }

    companion object {
        var currentDay: Int = 0
            private set
    }
}