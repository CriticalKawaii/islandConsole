package simulation.thread

import simulation.IslandSimulation

/**
 * Задача для роста растений на острове
 */
class PlantGrowthTask : Runnable {
    override fun run() {
        val countPlants = 20
        if (IslandSimulation.getInstance().timeNow >= 2) {
            IslandSimulation.getInstance().placePlants(countPlants / 2)
        } else {
            IslandSimulation.getInstance().placePlants(countPlants)
        }
    }
}