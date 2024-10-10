package basic.pacman

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScoreCount(score: String) {
    Spacer (modifier = Modifier.height(40.dp))
    Row(modifier = Modifier
        .fillMaxWidth()
        .border(
            border = BorderStroke(3.dp, Color.Red),
            shape = MaterialTheme.shapes.medium ),
        horizontalArrangement = Arrangement.Center){

        Text(text = "SCORE: ", fontSize = 35.sp, color = Color.White)
        Text(text = score, fontSize = 35.sp, color = Color.White)
    }
}