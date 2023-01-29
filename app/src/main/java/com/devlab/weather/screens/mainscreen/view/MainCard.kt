package com.devlab.weather.screens.mainscreen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.devlab.weather.MainActivity
import com.devlab.weather.R
import com.devlab.weather.screens.mainscreen.model.WeatherModel

@Composable
fun MainCard(
    currentDay: MutableState<WeatherModel>,
    onRefreshClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.9f)
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colors.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentDay.value.date,
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = MaterialTheme.colors.onSurface
                    ),
                )
                AsyncImage(
                    model = "https:" + currentDay.value.icon,
                    contentDescription = currentDay.value.condition,
                    modifier = Modifier.size(35.dp)
                )
            }
            Text(
                text = currentDay.value.city,
                style = TextStyle(
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
            Text(
                text = currentDay.value.currentTemp.ifEmpty {
                    with(currentDay.value) { "$minTemp / $maxTemp" }
                } + "°C",
                style = TextStyle(
                    fontSize = 60.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
            Text(
                text = currentDay.value.condition,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onSearchClick.invoke()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_search_24),
                        contentDescription = "search",
                        tint = MaterialTheme.colors.onSurface,
                    )
                }
                Text(
                    text = with(currentDay.value) {
                        "$minTemp / $maxTemp °C"
                    },
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onSurface
                    ),
                )
                IconButton(
                    onClick = {
                        onRefreshClick.invoke()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_cloud_sync_24),
                        contentDescription = "sync",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}