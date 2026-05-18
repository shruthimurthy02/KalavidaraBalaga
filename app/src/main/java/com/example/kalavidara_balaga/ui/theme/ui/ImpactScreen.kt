package com.kalavidara.balaga.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.kalavidara.balaga.navigation.Routes
import com.kalavidara.balaga.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImpactScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Our Impact", style = MaterialTheme.typography.headlineSmall, color = Gold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepForest)
            )
        },
        bottomBar = { BottomNavBar(navController, Routes.IMPACT) }
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
                    .background(Brush.linearGradient(listOf(DeepForest, Forest)))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🎭", fontSize = 56.sp)
                    Text("Preserving Karnataka's Heritage",
                        style = MaterialTheme.typography.headlineMedium, color = Gold, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    Text("Connecting rural talent with urban opportunities",
                        color = Gold.copy(0.7f), fontSize = 13.sp, modifier = Modifier.padding(top = 6.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                }
            }

            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                ImpactCard(
                    emoji = "🏛️",
                    title = "Cultural Preservation",
                    desc = "Many traditional folk art forms face the risk of dying out as younger generations seek urban employment. Kalavidara-Balaga makes folk arts financially viable, giving artists a reason to continue and teach their craft.",
                    stat = "🎨  500+ Artists Registered",
                    bgColors = listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2)),
                    borderColor = Color(0xFFFFB74D)
                )
                ImpactCard(
                    emoji = "💚",
                    title = "Creative Economy",
                    desc = "By connecting rural folk troupes with urban event managers, corporates, and city organizers, we help artists earn year-round income — not just during wedding and festival seasons.",
                    stat = "📈  3x More Bookings per Troupe",
                    bgColors = listOf(Color(0xFFE8F5E9), Color(0xFFC8E6C9)),
                    borderColor = Color(0xFF66BB6A)
                )
                ImpactCard(
                    emoji = "🌏",
                    title = "Cultural Soft Power",
                    desc = "Showcasing Karnataka's rich and diverse performance arts — from the thunderous Dollu Kunitha drums to the mystical Yakshagana theatre — as a source of pride and global cultural identity.",
                    stat = "🌍  Represented in 30 Districts",
                    bgColors = listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB)),
                    borderColor = Color(0xFF42A5F5)
                )

                // Art forms we support
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = WarmWhite,
                    border = BorderStroke(1.dp, BorderColor),
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("🎨 Art Forms We Support",
                            style = MaterialTheme.typography.headlineSmall, color = DeepRed)
                        Spacer(Modifier.height(10.dp))
                        val arts = listOf("🥁 Dollu Kunitha","🕯️ Pooja Kunitha","🐂 Goravara Kunitha",
                            "🎪 Yakshagana","🔔 Kamsale","💃 Kolata","🪘 Veeragase","🎵 Janapada")
                        androidx.compose.foundation.layout.FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            arts.forEach { art ->
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = Cream,
                                    border = BorderStroke(1.dp, BorderColor)
                                ) {
                                    Text(art, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp))
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun ImpactCard(emoji: String, title: String, desc: String, stat: String, bgColors: List<Color>, borderColor: Color) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.5.dp, borderColor),
        color = Color.Transparent
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Brush.linearGradient(bgColors))
            .padding(20.dp)) {
            Column {
                Text(emoji, fontSize = 36.sp)
                Text(title, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(top = 10.dp))
                Text(desc, fontSize = 13.sp, lineHeight = 20.sp, color = Color(0xFF4A3728), modifier = Modifier.padding(top = 8.dp))
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.Black.copy(alpha = 0.08f),
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Text(stat, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp))
                }
            }
        }
    }
}