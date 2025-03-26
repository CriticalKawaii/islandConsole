package lifeform

abstract class LifeForm(val weight:Double, val maxPopulation:Int, val name:String) {
    var row:Int = 0
    var column:Int = 0
}