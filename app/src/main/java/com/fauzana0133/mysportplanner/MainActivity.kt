package com.fauzana0133.mysportplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fauzana0133.mysportplanner.ui.theme.SportPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportPlannerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SportFormScreen()
                }
            }
        }
    }
}

@Composable
fun SportFormScreen() {
    // State / variabel untuk input pengguna
    var selectedSport by remember { mutableStateOf("") }
    var duration by remember { mutableFloatStateOf(30f) }
    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val selectedDays = remember { mutableStateMapOf<String, Boolean>() }
    days.forEach {
        if (!selectedDays.containsKey(it)) {
            selectedDays[it] = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Gambar Raster
        Image(
            painter = painterResource(id = R.drawable.olahraga),
            contentDescription = stringResource(R.string.image_description),
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown Pilih Olahraga
        Text(text = stringResource(R.string.choose_sport), fontSize = 20.sp)

        var expanded by remember { mutableStateOf(false) }
        val options = listOf("Jogging", "Yoga", "Football", "Push-Up")

        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(selectedSport.ifEmpty { stringResource(R.string.select_option) })
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { sport ->
                    DropdownMenuItem(
                        text = { Text(sport) },
                        onClick = {
                            selectedSport = sport
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Slider durasi
        Text(stringResource(R.string.choose_duration, duration.toInt()))
        Slider(value = duration, onValueChange = { duration = it }, valueRange = 10f..120f)

        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox pilih hari
        Text(stringResource(R.string.choose_days))
        days.forEach { day ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedDays[day] == true,
                    onCheckedChange = { checked -> selectedDays[day] = checked }
                )
                Text(text = day)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tombol Next (nanti kita hubungkan ke halaman berikutnya)
        Button(onClick = {
            // Akan diisi logika navigasi nanti
        }) {
            Text(stringResource(R.string.next))
        }
    }
}
