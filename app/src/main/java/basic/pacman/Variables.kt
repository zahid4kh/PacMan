package basic.pacman

import androidx.compose.ui.unit.dp

val arrowUp = R.drawable.arrow_up
val arrowDown = R.drawable.arrow_down
val arrowRight = R.drawable.arrow_right
val arrowLeft = R.drawable.arrow_left
val buttonSize = 90.dp

const val movingSpeed = 1f
const val spawnPosX = -50f
const val spawnPosY = -50f

//
//fun floatRange(start: Float, endInclusive: Float, step: Float = 5.0f): List<Float> {
//    require(step > 0) { "Step must be positive." }
//    val range = mutableListOf<Float>()
//    var current = start
//    while (current <= endInclusive) {
//        range.add(current)
//        current += step
//    }
//    return range
//}