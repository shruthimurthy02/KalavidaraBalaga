package com.kalavidara.balaga.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalavidara.balaga.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateHome: () -> Unit) {

    // Floating animation for the lotus emoji
    val infiniteTransition = rememberInfiniteTransition(label = "float")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = -12f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ), label = "floatY"
    )

    // Scale-in animation on appear
    var visible by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.7f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 300f),
        label = "scale"
    )

    LaunchedEffect(Unit) { visible = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(DeepRed, DarkRed, Ink),
                    radius = 1200f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(scale)
                .padding(32.dp)
        ) {
            // Lotus icon with float animation
            Text(
                text = "🪷",
                fontSize = 72.sp,
                modifier = Modifier.offset(y = offsetY.dp)
            )

            Spacer(Modifier.height(20.dp))

            // App title
            Text(
                text = "Kalavidara\nBalaga",
                style = MaterialTheme.typography.displayLarge,
                color = Gold,
                textAlign = TextAlign.Center,
                lineHeight = 42.sp
            )

            // Kannada subtitle
            Text(
                text = "ಕಲಾವಿದರ ಬಳಗ",
                style = MaterialTheme.typography.bodyMedium,
                color = Gold.copy(alpha = 0.7f),
                letterSpacing = 3.sp,
                modifier = Modifier.padding(top = 6.dp)
            )

            Text(
                text = "Folk Artist Talent Hub • Karnataka",
                style = MaterialTheme.typography.bodySmall,
                color = WarmWhite.copy(alpha = 0.5f),
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(Modifier.height(52.dp))

            // Get Started button
            Button(
                onClick = onNavigateHome,
                colors = ButtonDefaults.buttonColors(containerColor = Gold),
                shape = RoundedCornerShape(50.dp),
                contentPadding = PaddingValues(horizontal = 40.dp, vertical = 14.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "ಪ್ರಾರಂಭಿಸಿ  •  Get Started",
                    color = Ink,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}