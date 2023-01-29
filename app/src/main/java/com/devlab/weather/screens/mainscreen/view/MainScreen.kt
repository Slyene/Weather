package com.devlab.weather.screens.mainscreen.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.devlab.weather.R
import com.devlab.weather.screens.mainscreen.viewmodel.NetworkWeather
import com.devlab.weather.screens.mainscreen.model.WeatherModel

@Composable
fun MainScreen(context: Context) {
    val weather = NetworkWeather()
    val daysList = remember {
        mutableStateOf(listOf<WeatherModel>())
    }
    val currentDay = remember {
        mutableStateOf(WeatherModel())
    }

    var searchDialogTextState = remember {
        mutableStateOf("")
    }
    var isSearchDialogActiveState = remember {
        mutableStateOf(false)
    }

    if (isSearchDialogActiveState.value) {
        SearchDialog(isSearchDialogActiveState, searchDialogTextState) {
            weather.getData(
                searchDialogTextState.value,
                context,
                daysList,
                currentDay,
            )
        }
    }

    weather.getData("St. Petersburg", context, daysList, currentDay)

    Image(
        painter = painterResource(id = R.drawable.weather_bg),
        contentDescription = "img1",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
    ) {
        MainCard(currentDay,
            onRefreshClick = {
                weather.getData(
                    searchDialogTextState.value,
                    context,
                    daysList,
                    currentDay,
                )
            },
            onSearchClick = {
                isSearchDialogActiveState.value = true
            })
        TabLayout(daysList, currentDay)
    }
}

@Composable
fun SearchDialog(
    searchDialogState: MutableState<Boolean>,
    searchDialogTextState: MutableState<String>,
    onSubmit: (String) -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            searchDialogState.value = false
        },
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Enter a city")
                TextField(value = searchDialogTextState.value, onValueChange = {
                    searchDialogTextState.value = it
                })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                searchDialogState.value = false
                onSubmit(searchDialogTextState.value)
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                searchDialogState.value = false
            }) {
                Text(text = "Cancel")
            }
        },
    )
}

@Composable
fun isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        LocalContext.current, permission
    ) == PermissionChecker.PERMISSION_GRANTED
}