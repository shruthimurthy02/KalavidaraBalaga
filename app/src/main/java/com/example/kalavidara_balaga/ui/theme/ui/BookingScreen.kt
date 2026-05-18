package com.kalavidara.balaga.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.kalavidara.balaga.data.SampleData
import com.kalavidara.balaga.navigation.Routes
import com.kalavidara.balaga.ui.theme.*
import androidx.compose.foundation.background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(troupeId: Int, navController: NavController) {
    val troupe = SampleData.troupes.first { it.id == troupeId }

    var customerName by remember { mutableStateOf("") }
    var eventType    by remember { mutableStateOf("") }
    var eventDate    by remember { mutableStateOf("") }
    var location     by remember { mutableStateOf("") }
    var contactPhone by remember { mutableStateOf("") }
    var message      by remember { mutableStateOf("") }
    var showSuccess  by remember { mutableStateOf(false) }
    var showError    by remember { mutableStateOf(false) }

    val eventTypes = listOf("Wedding Ceremony","Religious Festival","Cultural Program","Corporate Event","Government Function","Private Party")
    var expandedDropdown by remember { mutableStateOf(false) }

    if (showSuccess) {
        AlertDialog(
            onDismissRequest = {},
            icon = { Text("🎉", fontSize = 48.sp) },
            title = { Text("Booking Request Sent!", style = MaterialTheme.typography.headlineMedium, color = Forest) },
            text = { Text("The troupe has been notified. They will contact you within 24 hours to confirm.", textAlign = androidx.compose.ui.text.style.TextAlign.Center) },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccess = false
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Forest),
                    shape = RoundedCornerShape(50.dp)
                ) { Text("Done ✓") }
            },
            containerColor = WarmWhite,
            shape = RoundedCornerShape(24.dp)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Troupe", style = MaterialTheme.typography.headlineSmall, color = Gold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = WarmWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepRed)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.linearGradient(listOf(DeepRed, DarkRed)))
                    .padding(20.dp)
            ) {
                Column {
                    Text("📋", fontSize = 36.sp)
                    Text("Booking Inquiry", style = MaterialTheme.typography.headlineMedium, color = Gold)
                    Text("For: ${troupe.name}", color = Gold.copy(0.7f), fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp))
                }
            }

            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {

                BookingField("Your Name", customerName, "e.g. Ramesh Kumar") { customerName = it }

                // Dropdown for event type
                ExposedDropdownMenuBox(expanded = expandedDropdown, onExpandedChange = { expandedDropdown = it }) {
                    OutlinedTextField(
                        value = eventType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("EVENT TYPE") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                        placeholder = { Text("Select event type…", color = Muted) },
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DeepRed, unfocusedBorderColor = BorderColor)
                    )
                    ExposedDropdownMenu(expanded = expandedDropdown, onDismissRequest = { expandedDropdown = false }) {
                        eventTypes.forEach { type ->
                            DropdownMenuItem(text = { Text(type) }, onClick = { eventType = type; expandedDropdown = false })
                        }
                    }
                }

                BookingField("Event Date", eventDate, "e.g. 2025-12-15") { eventDate = it }
                BookingField("Event Location", location, "e.g. Vidhana Soudha, Bengaluru") { location = it }
                BookingField("Your Contact Number", contactPhone, "e.g. +91 98765 43210") { contactPhone = it }

                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("MESSAGE / SPECIAL REQUIREMENTS") },
                    placeholder = { Text("Describe your event, guests count, duration…", color = Muted) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DeepRed, unfocusedBorderColor = BorderColor)
                )

                if (showError) {
                    Text("⚠️ Please fill all required fields", color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
                }

                Button(
                    onClick = {
                        if (customerName.isBlank() || eventType.isBlank() || eventDate.isBlank() || location.isBlank() || contactPhone.isBlank()) {
                            showError = true
                        } else {
                            showError = false
                            showSuccess = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepRed),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("🎉  Submit Booking Request", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun BookingField(label: String, value: String, placeholder: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label.uppercase(), fontSize = 11.sp) },
        placeholder = { Text(placeholder, color = Muted) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DeepRed,
            unfocusedBorderColor = BorderColor
        )
    )
}