package com.meditation.tally.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.meditation.tally.data.preferences.ThemeColor
import com.meditation.tally.data.preferences.ThemeMode

private fun lightColorSchemeFor(themeColor: ThemeColor) = when (themeColor) {
    ThemeColor.ORANGE -> lightColorScheme(
        primary = SaffronPrimary,
        onPrimary = Color.White,
        primaryContainer = SaffronSurfaceVariant,
        onPrimaryContainer = SaffronPrimaryVariant,
        secondary = SaffronSecondary,
        onSecondary = Color.White,
        secondaryContainer = SaffronSurfaceVariant,
        onSecondaryContainer = SaffronOnSurface,
        tertiary = SaffronTertiary,
        surface = SaffronSurface,
        surfaceVariant = SaffronSurfaceVariant,
        background = SaffronBackgroundTop,
        onSurface = SaffronOnSurface,
        onSurfaceVariant = SaffronOnSurfaceVariant,
        onBackground = SaffronOnSurface,
        outline = SaffronOutline
    )
    ThemeColor.GREEN -> lightColorScheme(
        primary = MintPrimary,
        onPrimary = Color.White,
        primaryContainer = MintSurfaceVariant,
        onPrimaryContainer = MintPrimaryVariant,
        secondary = MintSecondary,
        onSecondary = Color.White,
        secondaryContainer = MintSurfaceVariant,
        onSecondaryContainer = MintOnSurface,
        tertiary = MintTertiary,
        surface = MintSurface,
        surfaceVariant = MintSurfaceVariant,
        background = MintBackgroundTop,
        onSurface = MintOnSurface,
        onSurfaceVariant = MintOnSurfaceVariant,
        onBackground = MintOnSurface,
        outline = MintOutline
    )
    ThemeColor.BLUE -> lightColorScheme(
        primary = BluePrimary,
        onPrimary = Color.White,
        primaryContainer = BlueSurfaceVariant,
        onPrimaryContainer = BluePrimaryVariant,
        secondary = BlueSecondary,
        onSecondary = Color.White,
        secondaryContainer = BlueSurfaceVariant,
        onSecondaryContainer = BlueOnSurface,
        tertiary = BlueTertiary,
        surface = BlueSurface,
        surfaceVariant = BlueSurfaceVariant,
        background = BlueBackgroundTop,
        onSurface = BlueOnSurface,
        onSurfaceVariant = BlueOnSurfaceVariant,
        onBackground = BlueOnSurface,
        outline = BlueOutline
    )
    ThemeColor.PINK -> lightColorScheme(
        primary = PinkPrimary,
        onPrimary = Color.White,
        primaryContainer = PinkSurfaceVariant,
        onPrimaryContainer = PinkPrimaryVariant,
        secondary = PinkSecondary,
        onSecondary = Color.White,
        secondaryContainer = PinkSurfaceVariant,
        onSecondaryContainer = PinkOnSurface,
        tertiary = PinkTertiary,
        surface = PinkSurface,
        surfaceVariant = PinkSurfaceVariant,
        background = PinkBackgroundTop,
        onSurface = PinkOnSurface,
        onSurfaceVariant = PinkOnSurfaceVariant,
        onBackground = PinkOnSurface,
        outline = PinkOutline
    )
    ThemeColor.GREY -> lightColorScheme(
        primary = GreyPrimary,
        onPrimary = Color.White,
        primaryContainer = GreySurfaceVariant,
        onPrimaryContainer = GreyPrimaryVariant,
        secondary = GreySecondary,
        onSecondary = Color.White,
        secondaryContainer = GreySurfaceVariant,
        onSecondaryContainer = GreyOnSurface,
        tertiary = GreyTertiary,
        surface = GreySurface,
        surfaceVariant = GreySurfaceVariant,
        background = GreyBackgroundTop,
        onSurface = GreyOnSurface,
        onSurfaceVariant = GreyOnSurfaceVariant,
        onBackground = GreyOnSurface,
        outline = GreyOutline
    )
}

private fun darkColorSchemeFor(themeColor: ThemeColor) = when (themeColor) {
    ThemeColor.ORANGE -> darkColorScheme(
        primary = DarkSaffronPrimary,
        onPrimary = Color(0xFF1C1412),
        primaryContainer = DarkSaffronSurfaceVariant,
        onPrimaryContainer = DarkSaffronPrimaryVariant,
        secondary = DarkSaffronSecondary,
        onSecondary = Color(0xFF1C1412),
        secondaryContainer = DarkSaffronSurfaceVariant,
        onSecondaryContainer = DarkSaffronOnSurface,
        tertiary = DarkSaffronTertiary,
        surface = DarkSaffronSurface,
        surfaceVariant = DarkSaffronSurfaceVariant,
        background = DarkSaffronBackground,
        onSurface = DarkSaffronOnSurface,
        onSurfaceVariant = DarkSaffronOnSurfaceVariant,
        onBackground = DarkSaffronOnSurface,
        outline = DarkSaffronOutline
    )
    ThemeColor.GREEN -> darkColorScheme(
        primary = DarkMintPrimary,
        onPrimary = Color(0xFF0D2820),
        primaryContainer = DarkMintSurfaceVariant,
        onPrimaryContainer = DarkMintPrimaryVariant,
        secondary = DarkMintSecondary,
        onSecondary = Color(0xFF0D2820),
        secondaryContainer = DarkMintSurfaceVariant,
        onSecondaryContainer = DarkMintOnSurface,
        tertiary = DarkMintTertiary,
        surface = DarkMintSurface,
        surfaceVariant = DarkMintSurfaceVariant,
        background = DarkMintBackground,
        onSurface = DarkMintOnSurface,
        onSurfaceVariant = DarkMintOnSurfaceVariant,
        onBackground = DarkMintOnSurface,
        outline = DarkMintOutline
    )
    ThemeColor.BLUE -> darkColorScheme(
        primary = DarkBluePrimary,
        onPrimary = Color(0xFF0A1120),
        primaryContainer = DarkBlueSurfaceVariant,
        onPrimaryContainer = DarkBluePrimaryVariant,
        secondary = DarkBlueSecondary,
        onSecondary = Color(0xFF0A1120),
        secondaryContainer = DarkBlueSurfaceVariant,
        onSecondaryContainer = DarkBlueOnSurface,
        tertiary = DarkBlueTertiary,
        surface = DarkBlueSurface,
        surfaceVariant = DarkBlueSurfaceVariant,
        background = DarkBlueBackground,
        onSurface = DarkBlueOnSurface,
        onSurfaceVariant = DarkBlueOnSurfaceVariant,
        onBackground = DarkBlueOnSurface,
        outline = DarkBlueOutline
    )
    ThemeColor.PINK -> darkColorScheme(
        primary = DarkPinkPrimary,
        onPrimary = Color(0xFF1A0A12),
        primaryContainer = DarkPinkSurfaceVariant,
        onPrimaryContainer = DarkPinkPrimaryVariant,
        secondary = DarkPinkSecondary,
        onSecondary = Color(0xFF1A0A12),
        secondaryContainer = DarkPinkSurfaceVariant,
        onSecondaryContainer = DarkPinkOnSurface,
        tertiary = DarkPinkTertiary,
        surface = DarkPinkSurface,
        surfaceVariant = DarkPinkSurfaceVariant,
        background = DarkPinkBackground,
        onSurface = DarkPinkOnSurface,
        onSurfaceVariant = DarkPinkOnSurfaceVariant,
        onBackground = DarkPinkOnSurface,
        outline = DarkPinkOutline
    )
    ThemeColor.GREY -> darkColorScheme(
        primary = DarkGreyPrimary,
        onPrimary = Color(0xFF12171A),
        primaryContainer = DarkGreySurfaceVariant,
        onPrimaryContainer = DarkGreyPrimaryVariant,
        secondary = DarkGreySecondary,
        onSecondary = Color(0xFF12171A),
        secondaryContainer = DarkGreySurfaceVariant,
        onSecondaryContainer = DarkGreyOnSurface,
        tertiary = DarkGreyTertiary,
        surface = DarkGreySurface,
        surfaceVariant = DarkGreySurfaceVariant,
        background = DarkGreyBackground,
        onSurface = DarkGreyOnSurface,
        onSurfaceVariant = DarkGreyOnSurfaceVariant,
        onBackground = DarkGreyOnSurface,
        outline = DarkGreyOutline
    )
}

@Composable
fun MeditationTallyTheme(
    themeMode: ThemeMode,
    themeColor: ThemeColor = ThemeColor.ORANGE,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
    }

    val colorScheme = if (darkTheme) darkColorSchemeFor(themeColor) else lightColorSchemeFor(themeColor)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
