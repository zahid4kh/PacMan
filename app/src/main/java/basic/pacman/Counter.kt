package basic.pacman

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScoreCount(score: String, width: Int, height: Int) {
    Spacer (modifier = Modifier.height(40.dp))
    Row(modifier = Modifier
        .fillMaxWidth()
        .border(border = BorderStroke(3.dp, Color.Red), shape = MaterialTheme.shapes.medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){

        Text(text = "SCORE: $score", fontSize = 35.sp, color = Color.White,
            modifier = Modifier.padding(start = 5.dp))

        ShowScreenDensity(width,height)
    }
}

@Composable
fun ShowScreenDensity(width: Int, height: Int){
    val context = LocalContext.current
    val screenDensityPpi = remember { getScreenDensityDpi(context) }

    Column(verticalArrangement = Arrangement.SpaceAround) {
        Text(
            text = "Screen Density: $screenDensityPpi ppi", fontSize = 10.sp, color = Color.White,
            modifier = Modifier.padding(end = 15.dp)
        )

        Text(
            text = "Canvas Size: ${Pair(width,height)}", fontSize = 10.sp, color = Color.White,
            modifier = Modifier.padding(end = 5.dp)
        )
    }



}