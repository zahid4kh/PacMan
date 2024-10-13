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
import androidx.compose.runtime.mutableIntStateOf
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

    val pacmanInPx = dpToPx(40f, 41.6f)
//    val foodInPx = dpToPx(20f, 20f)

    val pacman = ContextCompat.getDrawable(context, R.drawable.pacman)
    val dollarSign = ContextCompat.getDrawable(context, R.drawable.food)
    val player = drawableToImageBitmap(pacman!!) // w = 40, h = 41.6
    val food = drawableToImageBitmap(dollarSign!!) // w = 20, h = 20

    var currentPlayerPosX by remember { mutableFloatStateOf(15f) }
    var currentPlayerPosY by remember { mutableFloatStateOf(15f) }
    var currentPlayerAngle by remember {mutableFloatStateOf ( 0F )}
    var scoreCounter by remember { mutableIntStateOf (0) }

    var currentFoodPosX by remember { mutableFloatStateOf(Random.nextInt(100..800).toFloat()) }
    var currentFoodPosY by remember { mutableFloatStateOf(Random.nextInt(100..800).toFloat()) }

    val playerCenter = Pair((currentPlayerPosX + 56.25).toInt(), (currentPlayerPosY + 58.5).toInt())
    val foodCenter = Pair((currentFoodPosX + 28.125).toInt(), (currentFoodPosY + 28.125).toInt())

    val canvasModifier = Modifier
        .fillMaxSize()
        .clip(shape = MaterialTheme.shapes.large)

    Column(modifier = Modifier.fillMaxSize().background(Color.Black)){
        ScoreCount(scoreCounter.toString())
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

                    val foodXRange = (0..(canvasWidth - 120).toInt() step 2).toList()
                    val foodYRange = 0..(canvasHeight - 120).toInt()

                    if (playerCenter.first in foodCenter.first - 5..foodCenter.first + 5 && playerCenter.second in foodCenter.second - 5..foodCenter.second + 5){
                        currentFoodPosX = foodXRange.random().toFloat()
                        currentFoodPosY = foodYRange.random().toFloat()
                        scoreCounter += 1
                    }

                    if (currentPlayerPosY > canvasHeight) {
                        currentPlayerPosY = spawnPosY
                    }
                    if (currentPlayerPosX > canvasWidth) {
                        currentPlayerPosX = spawnPosX
                    }
//                    println("Player position = ${Pair(currentPlayerPosX, currentPlayerPosY)}")
//                    println("Food position = ${Pair(currentFoodPosX, currentFoodPosY)}")
//                    println("Player Center = $playerCenter in PX")
//                    println("Food Center = $foodCenter in PX")

                    drawImage(
                        image = food,
                        topLeft = Offset(currentFoodPosX, currentFoodPosY)
                    )
                    rotate(degrees = currentPlayerAngle, pivot = Offset((pacmanInPx.first/2), (pacmanInPx.second/2))){ // TODO (rotation)
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
                    .background(Color.Gray),
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
