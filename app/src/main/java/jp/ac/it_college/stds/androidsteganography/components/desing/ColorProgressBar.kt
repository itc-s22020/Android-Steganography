package jp.ac.it_college.stds.androidsteganography.components.desing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun ColorProgressBar(
    maxValue: Int,
    currentValue: Int,
    modifier: Modifier) {
    val progress = if (currentValue > maxValue) 1f else currentValue.toFloat() / maxValue.toFloat()
    val progressBarColor = if (currentValue > maxValue) Color.Red else Color.Green

    LinearProgressIndicator(
        progress = progress,
        modifier = modifier
            .background(Color.DarkGray, shape = RoundedCornerShape(40.dp))
            .height(25.dp)
            .width(275.dp)
        ,
        color = progressBarColor,
        trackColor = Color.Gray,
        strokeCap = StrokeCap.Round
    )
}