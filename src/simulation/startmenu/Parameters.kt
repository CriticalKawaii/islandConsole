package simulation.startMenu

import field.IslandField
import simulation.IslandSimulation
import java.util.*

/**
 * Класс для изменения параметров симуляции на старте
 */
class Parameters {
    /**
     * Метод для изменения параметров симуляции
     */
    fun changeParameters() {
        changeIslandSize()
        var countPredators = changePredatorsSize()
        var countHerbivores = changeHerbivoresSize()
        var countPlants = changePlantsSize()

        if (countHerbivores == 0) {
            countHerbivores = IslandSimulation.getInstance().herbivoresCount
        }
        if (countPredators == 0) {
            countPredators = IslandSimulation.getInstance().predatorsCount
        }
        if (countPlants == 0) {
            countPlants = IslandSimulation.getInstance().plantsCount
        }

        IslandSimulation.getInstance().createIslandModel(countHerbivores, countPredators, countPlants)
    }

    /**
     * Метод для изменения размера острова
     */
    private fun changeIslandSize() {
        println("Хотите ли вы изменить размер острова (10x4)?")
        println("1. Да")
        println("2. Нет")
        print("Введите номер режима: ")
        val answer = takeInt(1, 2)
        if (answer == 1) {
            println("Введите желаемый размер острова!")
            println("Количество строк:")
            val rows = takeInt(1, 500)
            println("Количество столбцов:")
            val columns = takeInt(1, 500)
            IslandField.initializeLocations(rows, columns)
        } else {
            IslandField.initializeLocations()
        }
    }

    /**
     * Метод для изменения количества хищников
     * @return countPredators Количество хищников
     */
    private fun changePredatorsSize(): Int {
        println("Хотите ли вы изменить количество хищников (25)?")
        println("1. Да")
        println("2. Нет")
        print("Введите номер режима: ")
        var countPredators = 0
        val answer = takeInt(1, 2)
        if (answer == 1) {
            println("Введите желаемое количество хищников от 5 до 500!")
            println("Количество хищников:")
            countPredators = takeInt(5, 500)
        }
        return countPredators
    }

    /**
     * Метод для изменения количества травоядных
     * @return countHerbivores Количество травоядных
     */
    private fun changeHerbivoresSize(): Int {
        println("Хотите ли вы изменить количество травоядных (30)?")
        println("1. Да")
        println("2. Нет")
        print("Введите номер режима: ")
        var countHerbivores = 0
        val answer = takeInt(1, 2)

        if (answer == 1) {
            println("Введите желаемое количество травоядных от 10 до 500!")
            println("Количество травоядных:")
            countHerbivores = takeInt(10, 500)
        }
        return countHerbivores
    }

    /**
     * Метод для изменения количества растений
     * @return countPlants Количество растений
     */
    private fun changePlantsSize(): Int {
        println("Хотите ли вы изменить количество растений (10)?")
        println("1. Да")
        println("2. Нет")
        print("Введите номер режима: ")
        var countPlants = 0
        val answer = takeInt(1, 2)

        if (answer == 1) {
            println("Введите желаемое количество растений от 1 до 500!")
            println("Количество растений:")
            countPlants = takeInt(1, 500)
        }
        return countPlants
    }

    /**
     * Метод для считывания целого числа с клавиатуры в заданном диапазоне
     * @param lowNum Нижняя граница диапазона
     * @param highNum Верхняя граница диапазона
     * @return number Считанное число
     */
    fun takeInt(lowNum: Int, highNum: Int): Int {
        val scanner = Scanner(System.`in`)
        while (true) {
            if (scanner.hasNextInt()) {
                val number = scanner.nextInt()
                if (number in lowNum..highNum) {
                    return number
                } else {
                    println("Ошибка! Введенное число не находится в заданном диапазоне. Попробуйте еще раз:")
                }
            } else {
                scanner.next()
                println("Ошибка! Введено некорректное значение. Попробуйте еще раз:")
            }
        }
    }
}