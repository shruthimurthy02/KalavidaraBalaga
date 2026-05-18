package com.kalavidara.balaga.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kalavidara.balaga.data.SampleData
import com.kalavidara.balaga.data.model.Troupe
import com.kalavidara.balaga.navigation.Routes
import com.kalavidara.balaga.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    var selectedDistrict by remember { mutableStateOf("All") }
    var selectedArt by remember { mutableStateOf("All") }

    // Filter logic
    val filtered = SampleData.troupes.filter { t ->
        val matchDist = selectedDistrict == "All" || t.district == selectedDistrict
        val matchArt  = selectedArt == "All" || t.artForm == selectedArt
        val matchQ    = query.isBlank() ||
                t.name.contains(query, ignoreCase = true) ||
                t.artForm.contains(query, ignoreCase = true) ||
                t.district.contains(query, ignoreCase = true)
        matchDist && matchArt && matchQ
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Find Artists", style = MaterialTheme.typography.headlineSmall, color = Gold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepRed)
            )
        },
        bottomBar = { BottomNavBar(navController, Routes.SEARCH) }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {

            // Search field
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search artists or art forms…", color = Muted) },
                leadingIcon = { Icon(Icons.Default.Search, null, tint = DeepRed) },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DeepRed,
                    unfocusedBorderColor = BorderColor
                )
            )

            // District chips
            Text("District", color = Muted, fontSize = 11.sp,
                modifier = Modifier.padding(start = 16.dp, bottom = 6.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(SampleData.districts) { district ->
                    FilterChip(
                        selected = selectedDistrict == district,
                        onClick = { selectedDistrict = district },
                        label = { Text(district, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = DeepRed,
                            selectedLabelColor = WarmWhite
                        )
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Art form chips
            Text("Art Form", color = Muted, fontSize = 11.sp,
                modifier = Modifier.padding(start = 16.dp, bottom = 6.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(SampleData.artForms) { art ->
                    FilterChip(
                        selected = selectedArt == art,
                        onClick = { selectedArt = art },
                        label = { Text(art, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = DeepRed,
                            selectedLabelColor = WarmWhite
                        )
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Results
            if (filtered.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🎭", fontSize = 56.sp)
                        Text("No troupes found", color = Muted, modifier = Modifier.padding(top = 12.dp))
                        Text("Try a different filter", color = Muted, fontSize = 13.sp)
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filtered) { troupe ->
                        SearchResultCard(troupe) {
                            navController.navigate(Routes.profile(troupe.id))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultCard(troupe: Troupe, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = WarmWhite,
        shadowElevation = 3.dp,
        tonalElevation = 0.dp
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .then(Modifier), // extend with background gradient via Canvas if needed
                contentAlignment = Alignment.Center
            ) {
                Surface(shape = RoundedCornerShape(14.dp), color = DeepRed) {
                    Text(troupe.emoji, fontSize = 30.sp, modifier = Modifier.padding(10.dp))
                }
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(troupe.artForm, color = Saffron, fontSize = 10.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, letterSpacing = 1.sp)
                Text(troupe.name, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 15.sp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(top = 4.dp)) {
                    Text("📍 ${troupe.district}", color = Muted, fontSize = 11.sp)
                    Text("👥 ${troupe.memberCount} members", color = Muted, fontSize = 11.sp)
                }
                Text("⭐ ${troupe.rating}   ${troupe.artForm}", color = Gold, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}