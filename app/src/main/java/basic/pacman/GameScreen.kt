package basic.pacman

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlin.math.round
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
@Preview
fun GameScreen() {
    val context = LocalContext.current
    val screenDensityDpi = remember { getScreenDensityDpi(context) }

    val pacman = ContextCompat.getDrawable(context, R.drawable.pacman)
    val dollarSign = ContextCompat.getDrawable(context, R.drawable.food)
    val player = drawableToImageBitmap(pacman!!) // w = 40, h = 41.6
    val food = drawableToImageBitmap(dollarSign!!) // w = 20, h = 20

    val screenWidthDp = LocalConfiguration.current.screenWidthDp // 384
    val screenHeightDp = LocalConfiguration.current.screenHeightDp // 890

    val foodPosXRange = 30..(screenWidthDp - 120)
    val foodPosYRange = 30..(screenHeightDp - 120)

    val currentFoodPosX by remember { mutableStateOf(round(Random.nextInt(foodPosXRange).toDouble()).toInt()) }
    val currentFoodPosY by remember { mutableStateOf(round(Random.nextInt(foodPosYRange).toDouble()).toInt()) }
    //println("FoodPosX = $currentFoodPosX \nFoodPosY = $currentFoodPosY")

    var currentPlayerPosX by remember { mutableFloatStateOf(15f) }
    var currentPlayerPosY by remember { mutableFloatStateOf(15f) }
    var currentPlayerAngle by remember {mutableFloatStateOf ( 0F )}

    val scoreCounter by remember {mutableStateOf ("")}
    val canvasModifier = Modifier.fillMaxSize().clip(shape = MaterialTheme.shapes.large).background(Color.Blue)

    Column(modifier = Modifier.fillMaxSize().background(Color.Black)){

        ScoreCount(scoreCounter)
/////////////////////////////////////////   CANVAS STARTS HERE  ////////////////////////////////
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f).padding(horizontal = 10.dp, vertical = 10.dp)
            .border(border = BorderStroke(5.dp, Color.White), shape = MaterialTheme.shapes.medium)){

            Canvas(modifier = canvasModifier,
                onDraw = {
                    println("Canvas Size = ${listOf(size)}\nPlayer in PX = ${listOf(currentPlayerPosX, currentPlayerPosY)}")
                    drawImage(
                        image = food,
                        topLeft = Offset(currentFoodPosX.toFloat(), currentFoodPosY.toFloat()
                        )
                    )
                    rotate(degrees = currentPlayerAngle, pivot = Offset((player.height/2).toFloat(), (player.width/2).toFloat())){
                        drawImage(
                            image = player,
                            topLeft = Offset(currentPlayerPosX, currentPlayerPosY)
                        )
                    }

                }
            )
        }
////////////////////////////////////    CANVAS ENDS HERE    ///////////////////////////////////
        val playerInDpX = (currentPlayerPosX * 160) / screenDensityDpi
        val playerInDpY = (currentPlayerPosY * 160) / screenDensityDpi
        println("Player in DP = ${listOf(playerInDpX, playerInDpY)}")
        println("Screen in DP = ${listOf(screenWidthDp, screenHeightDp)}")
        var goUp by rememberSaveable {mutableStateOf(false)}
        var goDown by rememberSaveable {mutableStateOf(false)}
        var goLeft by rememberSaveable {mutableStateOf(false)}
        var goRight by rememberSaveable {mutableStateOf(false)}
        val movingSpeed = 5f
        val spawnPosX = -50f
        val spawnPosY = -10f
        when (true) {
            goUp ->  currentPlayerPosY -= movingSpeed
            goDown -> { currentPlayerPosY += movingSpeed; if (playerInDpY > screenHeightDp) currentPlayerPosY = spawnPosY }
            goLeft -> { currentPlayerPosX -= movingSpeed }
            goRight -> { currentPlayerPosX += movingSpeed; if (playerInDpX > screenWidthDp) currentPlayerPosX = spawnPosX }
            else -> null
        }
////////////////////////////////////  BUTTONS START HERE    //////////////////////
        Column(modifier = Modifier.fillMaxWidth()){
            Column(
                Modifier
                    .fillMaxSize()
                    .border(
                        border = BorderStroke(5.dp, Color.Green),
                        shape = MaterialTheme.shapes.large
                    )
                    .clip(shape = MaterialTheme.shapes.large)
                    .background(Color.White),
                verticalArrangement = Arrangement.Center){

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    IconButton(onClick = {goUp = true; goDown = false; goRight = false; goLeft = false}, // arrow up
                        Modifier.size(buttonSize)) {
                        Icon(painter = painterResource(arrowUp) , contentDescription = "arrow up",
                            Modifier.size(buttonSize))
                    }
                }

                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly){
                    IconButton(onClick = {goUp = false; goDown = false; goRight = false; goLeft = true}, // arrow left
                        Modifier.size(buttonSize)) {
                        Icon(painter = painterResource(arrowLeft) , contentDescription = "arrow left",
                            Modifier.size(buttonSize))
                    }

                    IconButton(onClick = {goUp = false; goDown = true; goRight = false; goLeft = false}, // arrow down
                        Modifier.size(buttonSize)) {
                        Icon(painter = painterResource(arrowDown) , contentDescription = "arrow down",
                            Modifier.size(buttonSize))
                    }

                    IconButton(onClick = {goUp = false; goDown = false; goRight = true; goLeft = false}, // arrow right
                        Modifier.size(buttonSize)) {
                        Icon(painter = painterResource(arrowRight) , contentDescription = "arrow right",
                            Modifier.size(buttonSize))
                    }
                }

            }
        }
    ////////////////////////////////////  BUTTONS END HERE    //////////////////////
    }
}

//Canvas size = [912, 1520] in Int
