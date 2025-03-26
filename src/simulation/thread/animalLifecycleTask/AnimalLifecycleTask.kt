package simulation.thread.animalLifecycleTask

import simulation.thread.animalLifecycleTask.task.AnimalEatTask
import simulation.thread.animalLifecycleTask.task.AnimalHpDecreaseTask
import simulation.thread.animalLifecycleTask.task.AnimalMoveTask
import simulation.thread.animalLifecycleTask.task.AnimalMultiplyTask
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

/**
 * Задача для жизненного цикла животных на острове
 */
class AnimalLifecycleTask : Runnable {
     val animalEatTask: AnimalEatTask
     val animalMultiplyTask: AnimalMultiplyTask
     val animalHpDecreaseTask: AnimalHpDecreaseTask
     val latch = CountDownLatch(3)

    init {
        animalEatTask = AnimalEatTask(latch)
        animalMultiplyTask = AnimalMultiplyTask(latch)
        animalHpDecreaseTask = AnimalHpDecreaseTask(latch)
    }

    override fun run() {
        val executorService = Executors.newFixedThreadPool(4)

        executorService.submit(animalEatTask) // животное ест
        executorService.submit(animalMultiplyTask) // животное размножается
        executorService.submit(animalHpDecreaseTask) // уменьшение здоровья
        try {
            latch.await() // ожидаем, пока CountDownLatch не достигнет нуля
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        executorService.submit(AnimalMoveTask()) // животные двигаются на другие локации
    }
}