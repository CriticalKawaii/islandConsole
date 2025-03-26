package lifeform.animal

import error.ObjectNotLifeFormException
import field.IslandField
import field.Location
import lifeform.LifeForm
import lifeform.plant.Plant
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.min

abstract class Animal(
    weight: Double,
    maxPopulation: Int,
    name: String,
    val step: Int,
    val maxHp: Double
) : LifeForm(weight, maxPopulation, name) {
    var hp: Double = maxHp

    /**
     * Проверяет, может ли животное съесть указанную пищу.
     *
     * @param food Пища, которую пытается съесть животное
     * @return true, если животное успешно съело пищу, иначе - false
     */
    fun eat(food: LifeForm?): Boolean {
        if (food == null) return false

        val chanceToEat = getChanceToEat(food.name)
        val successfulEat = ThreadLocalRandom.current().nextDouble() < chanceToEat

        if (successfulEat) {
            this.hp = min(this.hp + food.weight, this.maxHp) // Восстанавливаем HP

            val location = IslandField.getLocation(food.row, food.column)
            when (food) {
                is Animal -> location.removeAnimal(food)
                is Plant -> if (location.plants.contains(food)) location.removePlant(food)
                else -> return false
            }
        }
        return successfulEat
    }

    /**
     * Абстрактный метод для получения шанса съесть указанную пищу.
     *
     * @param foodName Имя пищи
     * @return Шанс съесть пищу
     */
    abstract fun getChanceToEat(foodName: String): Double

    /**
     * Абстрактный метод для размножения животного с партнером.
     *
     * @param partner Партнер для размножения
     */
//    open fun multiply(partner: Animal) {
//        if (this::class == partner::class) {
//            val location = IslandField.getLocation(this.row, this.column)
//            val newAnimal = this::class.constructors.first().call(this.weight, this.maxPopulation, this.name, this.step, this.maxHp)
//            IslandField.addAnimal(newAnimal, location.row, location.column)
//        }
//    }

    open fun multiply(partner: Animal) {
        if (this::class == partner::class) {
            val location = IslandField.getLocation(this.row, this.column)
            try {
                // Поиск конструктора с 5 параметрами: weight, maxPopulation, name, step, maxHp
                val constructor = this::class.constructors.find { it.parameters.size == 5 }
                    ?: throw IllegalStateException("Подходящий конструктор не найден для ${this::class.simpleName}")
                // Создание нового животного с использованием найденного конструктора
                val newAnimal = constructor.call(this.weight, this.maxPopulation, this.name, this.step, this.maxHp)
                // Явно устанавливаем координаты (если требуется)
                newAnimal.row = location.row
                newAnimal.column = location.column
                // Добавляем нового животного на поле
                IslandField.addAnimal(newAnimal, location.row, location.column)
                println("Новый ${this.name} создан на локации (${location.row}, ${location.column})")
            } catch (e: Exception) {
                //println("Ошибка при размножении ${this.name}: ${e.message}")
            }
        }
    }
    /**
     * Перемещает животное на случайное количество клеток в случайном направлении.
     */
    fun move() {
        val random = ThreadLocalRandom.current()
        var attempts = 10 // Ограничение попыток найти новую клетку

        while (attempts-- > 0) {
            val randomCells = random.nextInt(1, step + 1)
            val direction = random.nextInt(4)

            val (newRow, newColumn) = when (direction) {
                0 -> row - randomCells to column // Вверх
                1 -> row + randomCells to column // Вниз
                2 -> row to column - randomCells // Влево
                3 -> row to column + randomCells // Вправо
                else -> row to column
            }

            if (newRow in 0..<IslandField.numRows && newColumn in 0..<IslandField.numColumns) {
                IslandField.removeAnimal(this, row, column)
                row = newRow
                column = newColumn
                IslandField.addAnimal(this, newRow, newColumn)
                return
            }
        }
    }

}
