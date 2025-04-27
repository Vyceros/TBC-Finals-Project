package ge.fitness.auth.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import ge.fitness.core.presentation.design_system.icon.CommunityIcon
import ge.fitness.core.presentation.design_system.icon.NutritionIcon
import ge.fitness.core.presentation.design_system.icon.RunningManIcon


@Composable
fun getVectorFromString(key : String) : ImageVector{
    return when(key){
        "Running" -> RunningManIcon
        "Nutrition" -> NutritionIcon
        "Community"-> CommunityIcon
        else -> RunningManIcon
    }
}