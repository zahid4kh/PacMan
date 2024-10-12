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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
@Preview
fun GameScreen() {
    val context = LocalContext.current
    val screenDensityDpi = remember { getScreenDensityDpi(context) }

    fun dpToPx(width: Float, height: Float): Pair<Float, Float>{
        val widthInPx = (width * screenDensityDpi) / 160
        val heightInPx = (height * screenDensityDpi) / 160
        val sizeInPx = Pair(widthInPx, heightInPx)
        return sizeInPx
    }

//    val pacmanInPx = dpToPx(40f, 41.6f)
//    val foodInPx = dpToPx(20f, 20f)

    val pacman = ContextCompat.getDrawable(context, R.drawable.pacman)
    val dollarSign = ContextCompat.getDrawable(context, R.drawable.food)
    val player = drawableToImageBitmap(pacman!!) // w = 40, h = 41.6
    val food = drawableToImageBitmap(dollarSign!!) // w = 20, h = 20

//    val screenWidthDp = LocalConfiguration.current.screenWidthDp // 384
//    val screenHeightDp = LocalConfiguration.current.screenHeightDp // 890

    var currentFoodPosX by remember { mutableFloatStateOf(300f) }
    var currentFoodPosY by remember { mutableFloatStateOf(900f) }

    var currentPlayerPosX by remember { mutableFloatStateOf(15f) }
    var currentPlayerPosY by remember { mutableFloatStateOf(15f) }
    var currentPlayerAngle by remember {mutableFloatStateOf ( 0F )}
    val scoreCounter by remember {mutableStateOf ("")}

    val canvasModifier = Modifier
        .fillMaxSize()
        .clip(shape = MaterialTheme.shapes.large)
        .background(Color.Blue)

    Column(modifier = Modifier.fillMaxSize().background(Color.Black)){
        ScoreCount(scoreCounter)
/////////////////////////////////////////   CANVAS STARTS HERE  ////////////////////////////////
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .border(border = BorderStroke(5.dp, Color.White), shape = MaterialTheme.shapes.medium)){

            Canvas(modifier = canvasModifier,
                onDraw = {
                    val canvasWidth = size.width
                    val canvasHeight = size.height

//                    val foodXRange = floatRange(0f, (canvasWidth - pacmanInPx.first), 5f)
                    val foodXRange = 0..(canvasWidth - 120).toInt()
                    val foodYRange = 0..(canvasHeight - 120).toInt()

                    val newFoodX = Random.nextInt(foodXRange)
                    val newFoodY = Random.nextInt(foodYRange)

                    if (currentPlayerPosX.toInt() == newFoodX) currentFoodPosX = Random.nextInt(foodXRange).toFloat()
                    if (currentPlayerPosY.toInt() == newFoodY) currentFoodPosY = Random.nextInt(foodYRange).toFloat()

                    if (currentPlayerPosY > canvasHeight) {
                        currentPlayerPosY = spawnPosY
                    }
                    if (currentPlayerPosX > canvasWidth) {
                        currentPlayerPosX = spawnPosX
                    }
                    println("Player position = ${Pair(currentPlayerPosX, currentPlayerPosY)}")
                    println("Food position = ${Pair(currentFoodPosX, currentFoodPosY)}")

                    drawImage(
                        image = food,
                        topLeft = Offset(currentFoodPosX, currentFoodPosY)
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
        var goUp by rememberSaveable {mutableStateOf(false)}
        var goDown by rememberSaveable {mutableStateOf(false)}
        var goLeft by rememberSaveable {mutableStateOf(false)}
        var goRight by rememberSaveable {mutableStateOf(false)}

        when (true) {
            goUp ->  currentPlayerPosY -= movingSpeed
            goDown ->  currentPlayerPosY += movingSpeed
            goLeft -> currentPlayerPosX -= movingSpeed
            goRight -> currentPlayerPosX += movingSpeed
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
