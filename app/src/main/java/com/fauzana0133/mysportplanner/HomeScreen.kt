package com.fauzana0133.mysportplanner

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sport Planner+") },
                actions = {
                    var expanded by remember { mutableStateOf(false) }

                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Tentang Aplikasi") },
                            onClick = {
                                expanded = false
                                navController.navigate("about")
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        SportFormScreen(modifier = Modifier.padding(innerPadding), navController = navController)
    }
    val context = LocalContext.current
    val nameState = remember { mutableStateOf("") }
    val selectedSport = remember { mutableStateOf("") }
    val days = listOf("Senin", "Rabu", "Jumat")
    val selectedDays = remember { mutableStateMapOf<String, Boolean>() }

    days.forEach {
        if (!selectedDays.containsKey(it)) {
            selectedDays[it] = false
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = stringResource(R.string.enter_name))
        OutlinedTextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(R.string.choose_sport))
        var expanded by remember { mutableStateOf(false) }
        OutlinedButton(onClick = { expanded = true }) {
            Text(text = selectedSport.value.ifEmpty { "Pilih Olahraga" })
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            listOf("Jogging", "Yoga", "Renang").forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = {
                    selectedSport.value = it
                    expanded = false
                })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.choose_days))

        days.forEach {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedDays[it] ?: false,
                    onCheckedChange = { checked -> selectedDays[it] = checked }
                )
                Text(text = it)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (nameState.value.isEmpty()) {
                    Toast.makeText(context, "Nama belum diisi!", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate(
                        "summary/${nameState.value}/${selectedSport.value}/${
                            selectedDays.filter { it.value }.keys.joinToString(
                                ","
                            )
                        }"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.next))
        }

        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(R.drawable.olahraga),
            contentDescription = "Gambar olahraga",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
