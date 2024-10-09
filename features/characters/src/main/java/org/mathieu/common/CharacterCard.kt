package org.mathieu.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import org.mathieu.domain.models.character.Character

/**
 * Card that will display the avatar and the name of a character
 */
@Composable
fun CharacterCard(
    modifier: Modifier, character: Character
) =
    Row(
        modifier = modifier
            .shadow(5.dp)
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {

        SubcomposeAsyncImage(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape),
            model = character.avatarUrl,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(text = character.name)

    }