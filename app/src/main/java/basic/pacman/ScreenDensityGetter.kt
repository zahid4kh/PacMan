package basic.pacman

import android.content.Context
import android.util.DisplayMetrics

fun getScreenDensityDpi(context: Context): Int {
    val displayMetrics = DisplayMetrics()
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as android.view.WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    val density = displayMetrics.densityDpi
    return density
}
