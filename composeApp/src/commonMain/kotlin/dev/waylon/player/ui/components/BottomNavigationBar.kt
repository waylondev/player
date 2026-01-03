package dev.waylon.player.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.waylon.player.ui.theme.Corners

/**
 * Tech-style bottom navigation bar component
 */
@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    // Use Box to wrap BottomAppBar to solve the issue of bottom corners being clipped
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary,
            tonalElevation = 8.dp,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home recommendations
                BottomNavItem(
                    icon = Icons.Default.Home,
                    label = "推荐",
                    isSelected = selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )

                // Hot videos
                BottomNavItem(
                    icon = Icons.Default.Fireplace,
                    label = "热门",
                    isSelected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )

                // Rankings
                BottomNavItem(
                    icon = Icons.Default.Leaderboard,
                    label = "排行",
                    isSelected = selectedTab == 2,
                    onClick = { onTabSelected(2) }
                )

                // Search
                BottomNavItem(
                    icon = Icons.Default.Search,
                    label = "搜索",
                    isSelected = selectedTab == 3,
                    onClick = { onTabSelected(3) }
                )
            }
        }
    }
}

/**
 * Bottom navigation item component
 */
@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val itemColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val containerColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        Color.Transparent
    }

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(
                color = containerColor,
                shape = Corners.full
            )
            .height(56.dp)
            .padding(horizontal = 12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = itemColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = itemColor,
                modifier = Modifier.padding(top = 4.dp),
                lineHeight = 16.sp
            )
        }
    }
}