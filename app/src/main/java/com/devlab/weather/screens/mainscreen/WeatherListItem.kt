package com.devlab.weather.screens.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.devlab.weather.model.WeatherModel

@Composable
fun WeatherListItem(weatherItem: WeatherModel, currentDay: MutableState<WeatherModel>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                if (weatherItem.hour.isNotEmpty()) currentDay.value = weatherItem
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = weatherItem.date,
                    color = MaterialTheme.colors.onSurface,
                )
                Text(
                    text = weatherItem.condition,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(top = 7.dp)
                )
            }
            Text(
                text = with(weatherItem) {
                    currentTemp.ifEmpty { "$minTemp / $maxTemp" } + "°C"
                },
                color = MaterialTheme.colors.primary,
                fontSize = 25.sp,
                fontWeight = FontWeight.Light
            )
            AsyncImage(
                model = "https:" + weatherItem.icon,
                contentDescription = "item",
                modifier = Modifier.size(35.dp)
            )
        }
    }
}
