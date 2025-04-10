package com.fauzana0133.mysportplanner

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SummaryScreen(
    navController: NavController,
    name: String?,
    sport: String?,
    days: String?
) {
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = "Halo, $name!",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Olahraga pilihanmu: $sport")
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Hari berolahraga: $days")
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Jadwal Olahragaku 💪")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Hai, ini jadwal olahragaku:\nNama: $name\nOlahraga: $sport\nHari: $days"
                    )
                }
                context.startActivity(Intent.createChooser(shareIntent, "Bagikan via"))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Bagikan")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = { navController.navigate("home") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kembali")
        }
    }
}
