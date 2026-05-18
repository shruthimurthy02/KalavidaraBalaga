package com.kalavidara.balaga.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.kalavidara.balaga.data.SampleData
import com.kalavidara.balaga.data.model.Troupe
import com.kalavidara.balaga.navigation.Routes
import com.kalavidara.balaga.ui.theme.*

@Composable
fun HomeScreen(navController: NavController) {
    val troupes = SampleData.troupes
    val featured = troupes.first { it.isFeatured }

    Scaffold(
        bottomBar = { BottomNavBar(navController, Routes.HOME) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ── Hero Header ──
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.verticalGradient(listOf(DeepRed, DarkRed)))
                        .padding(20.dp)
                ) {
                    Column {
                        Text("🙏 ನಮಸ್ಕಾರ", color = Gold.copy(0.7f), fontSize = 12.sp, letterSpacing = 2.sp)
                        Text("Kalavidara-Balaga", style = MaterialTheme.typography.headlineLarge, color = Gold)
                        Text("Discover Karnataka's Folk Artists", color = WarmWhite.copy(0.7f), fontSize = 13.sp)

                        Spacer(Modifier.height(16.dp))

                        // Search bar — tapping navigates to Search
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate(Routes.SEARCH) },
                            shape = RoundedCornerShape(14.dp),
                            color = WarmWhite,
                            shadowElevation = 6.dp
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Icon(Icons.Default.Search, contentDescription = null, tint = DeepRed)
                                Spacer(Modifier.width(8.dp))
                                Text("Search troupes, art forms, districts…", color = Muted, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }

            // ── Art Form Category Chips ──
            item {
                Text(
                    "🎨 Art Forms",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 12.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val categories = listOf(
                        "🎭" to "All Forms",
                        "🥁" to "Dollu Kunitha",
                        "🕯️" to "Pooja Kunitha",
                        "🐂" to "Goravara Kunitha",
                        "🎪" to "Yakshagana",
                        "🔔" to "Kamsale",
                    )
                    items(categories) { (emoji, name) ->
                        ArtFormCard(emoji = emoji, name = name) {
                            // Navigate to search with pre-selected art form filter
                            navController.navigate(Routes.SEARCH)
                        }
                    }
                }
            }

            // ── Featured Troupe ──
            item {
                Text(
                    "⭐ Featured Troupe",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 12.dp)
                )
                FeaturedCard(troupe = featured) {
                    navController.navigate(Routes.profile(featured.id))
                }
            }

            // ── All Troupes Grid ──
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🎶 All Troupes", style = MaterialTheme.typography.headlineSmall)
                    TextButton(onClick = { navController.navigate(Routes.SEARCH) }) {
                        Text("See All →", color = DeepRed)
                    }
                }
            }

            // Grid: 2-column LazyVerticalGrid simulation inside LazyColumn
            items(troupes.chunked(2)) { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { troupe ->
                        TroupeMiniCard(
                            troupe = troupe,
                            modifier = Modifier.weight(1f)
                        ) { navController.navigate(Routes.profile(troupe.id)) }
                    }
                    // Fill empty slot if odd number
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(12.dp))
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

// ── Reusable Composables ──

@Composable
fun ArtFormCard(emoji: String, name: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = WarmWhite,
        border = BorderStroke(1.5.dp, BorderColor),
        shadowElevation = 2.dp,
        modifier = Modifier.width(96.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 8.dp)
        ) {
            Text(emoji, fontSize = 28.sp)
            Spacer(Modifier.height(6.dp))
            Text(name, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@Composable
fun FeaturedCard(troupe: Troupe, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .background(Brush.linearGradient(listOf(Forest, DeepForest)))
                .padding(20.dp)
        ) {
            // Top-right badge
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Gold,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Text("✨ Top Rated", fontSize = 10.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), color = Ink)
            }

            Column {
                Text(troupe.artForm, color = Gold.copy(0.7f), fontSize = 11.sp, letterSpacing = 2.sp)
                Text(troupe.name, style = MaterialTheme.typography.headlineMedium, color = Gold)
                Text("📍 ${troupe.district}  •  ${troupe.memberCount} members",
                    color = WarmWhite.copy(0.6f), fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp))
                Text(troupe.description.take(120) + "…",
                    color = WarmWhite.copy(0.75f), fontSize = 13.sp,
                    lineHeight = 20.sp, modifier = Modifier.padding(top = 10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("⭐ ${troupe.rating}  (${troupe.totalEvents} events)", color = Gold, fontSize = 14.sp)
                    Button(
                        onClick = onClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Gold),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp)
                    ) {
                        Text("View Profile", color = Ink, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun TroupeMiniCard(troupe: Troupe, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val gradients = listOf(
        listOf(DeepRed, Saffron),
        listOf(Forest, DeepForest),
        listOf(androidx.compose.ui.graphics.Color(0xFF6B0C5E), androidx.compose.ui.graphics.Color(0xFFB5218A)),
        listOf(androidx.compose.ui.graphics.Color(0xFF0C3B6B), androidx.compose.ui.graphics.Color(0xFF1A6BB5)),
    )
    val gradient = gradients[troupe.id % gradients.size]

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = WarmWhite,
        border = BorderStroke(1.dp, BorderColor),
        shadowElevation = 2.dp,
        modifier = modifier
    ) {
        Column {
            // Image area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Brush.linearGradient(gradient)),
                contentAlignment = Alignment.Center
            ) {
                Text(troupe.emoji, fontSize = 40.sp)
            }
            // Info
            Column(modifier = Modifier.padding(10.dp)) {
                Text(troupe.artForm, color = Saffron, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Text(troupe.name, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 13.sp), maxLines = 2)
                Text("📍 ${troupe.district}", color = Muted, fontSize = 11.sp, modifier = Modifier.padding(top = 2.dp))
                Text("⭐ ${troupe.rating}", color = Gold, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}