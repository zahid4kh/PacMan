package basic.pacman

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap


@Composable
fun drawableToImageBitmap(drawable: Drawable): ImageBitmap {
    val bitmap = drawableToBitmap(drawable)
    return bitmapToImageBitmap(bitmap)
}

fun drawableToBitmap(drawable: Drawable): Bitmap {
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = android.graphics.Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun bitmapToImageBitmap(bitmap: Bitmap): ImageBitmap {
    return bitmap.asImageBitmap()
}