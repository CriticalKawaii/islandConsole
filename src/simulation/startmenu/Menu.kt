package simulation.startMenu

import field.IslandField
import simulation.IslandSimulation

/**
 * Класс для работы с меню начала симуляции
 */
class Menu {
    private val parameters = Parameters()

    /**
     * Метод для запуска симуляции
     */
    fun startSimulation() {
        println("----------------------------------")
        println("Хотите ли вы внести свои параметры перед началом симуляции?")
        println("1. Да")
        println("2. Нет")
        print("Введите номер режима: ")
        val answer = parameters.takeInt(1, 2)

        if (answer == 1) {
            parameters.changeParameters()
        } else {
            IslandField.initializeLocations()
            IslandSimulation.getInstance().createIslandModel()
        }
        println("----------------------------------")
        println("Загрузка симуляции острова...")
        println("----------------------------------")
        println()
    }
}