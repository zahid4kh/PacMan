package basic.pacman

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun GameScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.Black)){

        ScoreCount()

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .border(
                border = BorderStroke(7.dp, Color.White),
                shape = MaterialTheme.shapes.medium )
        ){

        }

        Column(modifier = Modifier
            .fillMaxWidth()){
            Buttons()
        }

    }


}