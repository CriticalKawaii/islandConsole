package simulation.thread.animalLifecycleTask.task

import field.IslandField
import lifeform.animal.Animal

/**
 * Задача для перемещения животных на острове
 */
class AnimalMoveTask : Runnable {
    override fun run() {
        val animals: List<Animal> = IslandField.getAllAnimals().filter { it.step > 0 }
        for (animal in animals) {
            animal.move()
        }
    }
}
