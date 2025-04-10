package com.fauzana0133.mysportplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fauzana0133.mysportplanner.ui.theme.SportPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportPlannerTheme {
                val navController = rememberNavController()

                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(navController, startDestination = "home") {
                        composable("home") {
                            SportFormScreen(navController = navController)
                        }
                        composable(
                            "summary/{name}/{sport}/{days}",
                            arguments = listOf(
                                navArgument("name") { type = NavType.StringType },
                                navArgument("sport") { type = NavType.StringType },
                                navArgument("days") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            SummaryScreen(
                                navController,
                                name = backStackEntry.arguments?.getString("name"),
                                sport = backStackEntry.arguments?.getString("sport"),
                                days = backStackEntry.arguments?.getString("days")
                            )
                        }
                        composable("about") {
                            AboutScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportFormScreen(navController: NavController, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    var userName by remember { mutableStateOf("") }
    var selectedSport by remember { mutableStateOf("") }
    var duration by remember { mutableFloatStateOf(30f) }

    val options = listOf("Jogging", "Yoga", "Football", "Push-Up")
    var expanded by remember { mutableStateOf(false) }

    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val selectedDays = remember { mutableStateMapOf<String, Boolean>() }
    days.forEach {
        if (!selectedDays.containsKey(it)) selectedDays[it] = false
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Sport Planner+",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("about")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About App"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
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

            // Input Nama
            Text(
                text = stringResource(R.string.enter_name),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text(stringResource(R.string.name)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Pilih Olahraga
            Text(
                text = stringResource(R.string.choose_sport),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
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
            Text(
                text = stringResource(R.string.choose_duration, duration.toInt()),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Slider(
                value = duration,
                onValueChange = { duration = it },
                valueRange = 10f..120f
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Checkbox pilih hari
            Text(
                text = stringResource(R.string.choose_days),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Column {
                days.forEach { day ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = selectedDays[day] == true,
                            onCheckedChange = { checked -> selectedDays[day] = checked }
                        )
                        Text(text = day)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Next
            Button(
                onClick = {
                    val selectedDayString = selectedDays.filterValues { it }.keys.joinToString(", ")
                    navController.navigate("summary/${userName}/${selectedSport}/${selectedDayString}")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.next))
            }
        }
    }
}

