package com.kalavidara.balaga.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.kalavidara.balaga.R

// Google Fonts setup
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val YatraOne = FontFamily(
    Font(GoogleFont("Yatra One"), provider)
)

val DMSans = FontFamily(
    Font(GoogleFont("DM Sans"), provider),
    Font(GoogleFont("DM Sans"), provider, weight = FontWeight.Medium),
    Font(GoogleFont("DM Sans"), provider, weight = FontWeight.SemiBold),
    Font(GoogleFont("DM Sans"), provider, weight = FontWeight.Bold),
)

val AppTypography = Typography(
    displayLarge  = TextStyle(fontFamily = YatraOne, fontSize = 36.sp),
    headlineLarge = TextStyle(fontFamily = YatraOne, fontSize = 28.sp),
    headlineMedium= TextStyle(fontFamily = YatraOne, fontSize = 22.sp),
    headlineSmall = TextStyle(fontFamily = YatraOne, fontSize = 18.sp),
    bodyLarge     = TextStyle(fontFamily = DMSans, fontSize = 16.sp),
    bodyMedium    = TextStyle(fontFamily = DMSans, fontSize = 14.sp),
    bodySmall     = TextStyle(fontFamily = DMSans, fontSize = 12.sp),
    labelSmall    = TextStyle(fontFamily = DMSans, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
)