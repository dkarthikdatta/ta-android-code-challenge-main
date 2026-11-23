package com.example.mytripactions.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun CounterScreen() {
    var count by remember {
        mutableIntStateOf(0)
    }

    Column {
        Text("Hello Android")
        Text(text = "Count value is $count")
        Button(onClick = {
            count++
        }) {
            Text(text = "Increment")
        }
    }
}