package simulation.thread.animalLifecycleTask.task

import field.IslandField
import field.Location
import lifeform.animal.Animal
import java.util.concurrent.CountDownLatch

/**
 * Задача для размножения животных на острове
 */
class AnimalMultiplyTask(private val latch: CountDownLatch) : Runnable {
    var babies: Int = 0
        private set

    override fun run() {
        babies = 0
        val animals: List<Animal> = IslandField.getAllAnimals()
        val animalsMultiplied = mutableListOf<Animal>()

        if (animals.isNotEmpty()) {
            for (currentAnimal in animals) {
                if (!animalsMultiplied.contains(currentAnimal)) {
                    val location: Location = IslandField.getLocation(currentAnimal.row, currentAnimal.column)
                    var locationAnimals: List<Animal> = location.animals

                    if (locationAnimals.size > 1) {
                        locationAnimals.filter { it.name == currentAnimal.name && it != currentAnimal }
                            .also { locationAnimals = it }

                        if (locationAnimals.isNotEmpty()) {
                            val partner = locationAnimals[0]

                            if (!animalsMultiplied.contains(partner)) {
                                currentAnimal.multiply(partner)

                                animalsMultiplied.add(currentAnimal)
                                animalsMultiplied.add(partner)

                                babies++
                            }
                        }
                    }
                }
            }
        }

        latch.countDown()
    }
}
