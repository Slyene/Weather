package com.devlab.weather.screens.mainscreen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devlab.weather.R
import com.devlab.weather.model.NetworkWeather
import com.devlab.weather.model.WeatherModel


@Composable
fun MainScreen(context: Context) {
    val weather = NetworkWeather()
    val daysList = remember {
        mutableStateOf(listOf<WeatherModel>())
    }
    val currentDay = remember {
        mutableStateOf(WeatherModel())
    }
    weather.getData("St. Petersburg", context, daysList, currentDay)

    Image(
        painter = painterResource(id = R.drawable.weather_bg),
        contentDescription = "img1",
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
    ) {
        MainCard(currentDay)
        TabLayout(daysList, currentDay)
    }
}