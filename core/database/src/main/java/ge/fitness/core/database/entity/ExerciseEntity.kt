package ge.fitness.core.database.entity


data class ExerciseEntity(
    val id: String = "",
    val name: String = "",
    val bodyPart: String = "",
    val equipment: String = "",
    val target: String = "",
    val secondaryMuscles: List<String> = emptyList(),
    val instructions: List<String> = emptyList(),
    val gifUrl: String = ""
){
    // firestore needs constructor with no arguments so we do this here
    constructor() : this("", "", "", "", "", emptyList(), emptyList(), "")
}

