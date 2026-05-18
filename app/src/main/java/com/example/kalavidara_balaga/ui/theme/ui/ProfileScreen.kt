package com.kalavidara.balaga.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.kalavidara.balaga.data.SampleData
import com.kalavidara.balaga.navigation.Routes
import com.kalavidara.balaga.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(troupeId: Int, navController: NavController) {
    val troupe = SampleData.troupes.first { it.id == troupeId }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(troupe.name, style = MaterialTheme.typography.headlineSmall, color = Gold, maxLines = 1) },
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
            // ── Cover photo ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Brush.linearGradient(listOf(DeepRed, DarkRed))),
                contentAlignment = Alignment.Center
            ) {
                Text(troupe.emoji, fontSize = 80.sp)
            }

            // ── Profile meta ──
            Column(modifier = Modifier.padding(16.dp)) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Forest,
                    modifier = Modifier
                        .size(80.dp)
                        .offset(y = (-20).dp),
                    shadowElevation = 8.dp
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(troupe.emoji, fontSize = 36.sp)
                    }
                }

                Text(troupe.name, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.offset(y = (-12).dp))

                Surface(shape = RoundedCornerShape(20.dp), color = Gold, modifier = Modifier.offset(y = (-8).dp)) {
                    Text(troupe.artForm, fontSize = 11.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), color = Ink)
                }

                // Availability badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .background(androidx.compose.ui.graphics.Color(0xFFE8F5E9), RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Box(modifier = Modifier
                        .size(7.dp)
                        .background(androidx.compose.ui.graphics.Color(0xFF4CAF50), androidx.compose.foundation.shape.CircleShape))
                    Spacer(Modifier.width(6.dp))
                    Text("Available for Booking", color = androidx.compose.ui.graphics.Color(0xFF2E7D32), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            // ── Info Grid ──
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoItem("📍 District", troupe.district, Modifier.weight(1f))
                InfoItem("🗺️ Service Area", troupe.serviceArea, Modifier.weight(1f))
            }
            Spacer(Modifier.height(12.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoItem("👤 Lead Contact", troupe.leadContact, Modifier.weight(1f))
                InfoItem("👥 Members", "${troupe.memberCount} Members", Modifier.weight(1f))
            }

            // ── Description ──
            Text(troupe.description,
                fontSize = 14.sp, lineHeight = 22.sp, color = androidx.compose.ui.graphics.Color(0xFF5A3D2B),
                modifier = Modifier.padding(16.dp))

            // ── Action buttons ──
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = { navController.navigate(Routes.booking(troupe.id)) },
                    modifier = Modifier.weight(2f),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepRed),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(vertical = 14.dp)
                ) { Text("📋 Book This Troupe", fontWeight = FontWeight.Bold) }

                Button(
                    onClick = {
                        // Android native phone call intent
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${troupe.phone}"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Forest),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(vertical = 14.dp)
                ) { Text("📞 Call", fontWeight = FontWeight.Bold) }
            }

            Spacer(Modifier.height(20.dp))

            // ── Gallery ──
            SectionTitle("🖼️ Portfolio Gallery")
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                userScrollEnabled = false
            ) {
                val colors = listOf(DeepRed, Forest, androidx.compose.ui.graphics.Color(0xFF6B0C5E), Saffron,
                    androidx.compose.ui.graphics.Color(0xFF0C3B6B), androidx.compose.ui.graphics.Color(0xFF7B3F00))
                items(troupe.galleryEmojis) { emoji ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(colors[troupe.galleryEmojis.indexOf(emoji) % colors.size], RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) { Text(emoji, fontSize = 28.sp) }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── Equipment ──
            SectionTitle("🥁 Equipment")
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(14.dp),
                color = androidx.compose.ui.graphics.Color(0xFFFFF8E8),
                border = BorderStroke(1.5.dp, Turmeric)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    troupe.equipment.forEach { item ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text("🥁  ", fontSize = 14.sp)
                            Text(item, fontSize = 13.sp, color = Ink)
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── Rating ──
            SectionTitle("⭐ Rating")
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(14.dp),
                color = WarmWhite,
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(troupe.rating.toString(), fontSize = 36.sp, fontWeight = FontWeight.Bold)
                    Text("⭐".repeat(troupe.rating.toInt()), fontSize = 20.sp)
                    Text("Based on ${troupe.totalEvents} events", color = Muted, fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp))
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun InfoItem(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(shape = RoundedCornerShape(12.dp), color = WarmWhite, border = BorderStroke(1.dp, BorderColor), modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(label, color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 3.dp))
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(start = 16.dp, bottom = 12.dp))
}