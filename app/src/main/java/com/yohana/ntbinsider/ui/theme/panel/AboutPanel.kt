package com.yohana.ntbinsider.ui.theme.panel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yohana.ntbinsider.R

@Composable
fun AboutPanel() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(R.drawable.yohana), contentDescription = null, modifier = Modifier
                .size(250.dp)
                .clip(
                    CircleShape
                ))

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Yohana Putrisia Sitohang", style = MaterialTheme.typography.h5)
            Text(text = "sitohangyohana1110@gmail.com", color = Color.Gray)
        }
    }
}