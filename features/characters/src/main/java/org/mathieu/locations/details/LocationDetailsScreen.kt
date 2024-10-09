package org.mathieu.locations.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.mathieu.common.CharacterCard
import org.mathieu.ui.composables.PreviewContent

private typealias UIState = LocationDetailsState


@Composable
fun LocationDetailsScreen(
    navController: NavController,
    id: Int
){
    val viewModel: LocationDetailsViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    viewModel.init(locationId = id)

    LocationDetailsContent(
        state = state,
        onClickBack = navController::popBackStack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationDetailsContent(
    state: UIState = UIState(),
    onClickBack: () -> Unit = {}
)= Scaffold(topBar = {

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
            text = state.location.second +" : "+ state.location.first,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}) { paddingValues ->
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
    ) {
        Text(
            text = "Liste des résidents :"
        )

        LazyColumn {

            items(state.residents) {
                CharacterCard(
                    modifier = Modifier
                        .padding(8.dp),
                    character = it
                )
            }

        }
    }
}



@Preview
@Composable
private fun LocationDetailsPreview() = PreviewContent {
    LocationDetailsContent()
}