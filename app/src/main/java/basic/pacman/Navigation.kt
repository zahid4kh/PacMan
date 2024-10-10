package basic.pacman

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun Buttons(){
    val arrowUp = R.drawable.arrow_up
    val arrowDown = R.drawable.arrow_down
    val arrowRight = R.drawable.arrow_right
    val arrowLeft = R.drawable.arrow_left
    val buttonSize = 90.dp

    Column(Modifier
        .fillMaxSize()
        .border(
            border = BorderStroke(5.dp, Color.Green),
            shape = MaterialTheme.shapes.large )
        .clip(shape = MaterialTheme.shapes.large)
        .background(Color.White),
        verticalArrangement = Arrangement.Center){

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            IconButton(onClick = {}, Modifier.size(buttonSize)) {
                Icon(painter = painterResource(arrowUp) , contentDescription = "arrow up",
                    Modifier.size(buttonSize))
            }
        }

        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly){
            IconButton(onClick = {},
                Modifier.size(buttonSize)) {
                Icon(painter = painterResource(arrowLeft) , contentDescription = "arrow left",
                    Modifier.size(buttonSize))
            }

            IconButton(onClick = {},
                Modifier.size(buttonSize)) {
                Icon(painter = painterResource(arrowDown) , contentDescription = "arrow down",
                    Modifier.size(buttonSize))
            }

            IconButton(onClick = {},
                Modifier.size(buttonSize)) {
                Icon(painter = painterResource(arrowRight) , contentDescription = "arrow right",
                    Modifier.size(buttonSize))
            }
        }

    }
}