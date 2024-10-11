package basic.pacman

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat


@Composable
@Preview
fun Player(){
    val context = LocalContext.current

    val pacman = ContextCompat.getDrawable(context, R.drawable.pacman)
    val dollarSign = ContextCompat.getDrawable(context, R.drawable.food)
    val player = drawableToImageBitmap(pacman!!) // w = 40, h = 41.6
    val food = drawableToImageBitmap(dollarSign!!) // w = 20, h = 20

    Canvas(modifier = Modifier.fillMaxSize()
        .clip(shape = MaterialTheme.shapes.large).padding(vertical = 20.dp, horizontal = 20.dp)
        .background(Color.Blue),
        onDraw = {
            val canvasWidth = size.width - 50
            val canvasHeight = size.height
            drawImage(
                image = food,
                topLeft = Offset(canvasWidth, canvasHeight)
            )
            drawImage(image = player, topLeft = Offset(canvasWidth, 0f))
        }
    )
}

// height = 1632, width = 1052 -> canvas size