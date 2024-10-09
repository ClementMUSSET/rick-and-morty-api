package org.mathieu.characters.details

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import org.mathieu.ui.Destination
import org.mathieu.ui.composables.PreviewContent
import org.mathieu.ui.navigate

private typealias UIState = CharacterDetailsState

@Composable
fun CharacterDetailsScreen(
    navController: NavController,
    id: Int
) {
    val viewModel: CharacterDetailsViewModel = viewModel()
    val state by viewModel.state.collectAsState()


    viewModel.init(characterId = id)

    CharacterDetailsContent(
        state = state,
        onClickBack = navController::popBackStack,
        onLocationClick = {
            navController.navigate(Destination.LocationDetails(state.location.second.toString()))
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun CharacterDetailsContent(
    state: UIState = UIState(),
    onClickBack: () -> Unit = { },
    onLocationClick: () -> Unit = { }
) = Scaffold(topBar = {

    Row(
        modifier = Modifier
            .background(org.mathieu.ui.theme.Purple40)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(16.dp)
                .clickable(onClick = onClickBack),
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.White)
        )

        Text(
            text = state.name,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )


    }
}) { paddingValues ->
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues), contentAlignment = Alignment.Center) {
        AnimatedContent(targetState = state.error != null, label = "") {
            state.error?.let { error ->
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = error,
                    textAlign = TextAlign.Center,
                    color = org.mathieu.ui.theme.Purple40,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 36.sp
                )
            } ?: Box(modifier = Modifier.fillMaxSize()) {

                Box(Modifier.align(Alignment.TopCenter)) {

                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .blur(100.dp)
                            .alpha(0.3f)
                            .fillMaxWidth(),
                        model = state.avatarUrl,
                        contentDescription = null
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background
                                    )
                                )
                            )
                    )


                }

                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .shadow(3.dp),
                        model = state.avatarUrl,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = state.name)

                    LocationCard(
                        name = state.location.first,
                        type = state.locationType,
                        onClick = onLocationClick
                    )
                }


            }
        }
    }
}

@Composable
private fun LocationCard(
    name:String,
    type:String,
    onClick: () -> Unit)
{
    val context = LocalContext.current
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                if (vibrator.hasVibrator()) {
                    val vibratorEffect =
                        VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
                    vibrator.vibrate(vibratorEffect)
                    Log.d("CharacterDetailsScreen","good")
                }
                onClick()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row (
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = type +" : "+ name,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
private fun CharacterDetailsPreview() = PreviewContent {
    CharacterDetailsContent()
}

