package dev.waylon.player.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.waylon.player.ui.theme.Corners

/**
 * 科技感顶部导航栏组件 (TV UI 设计)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onLoginClick: () -> Unit
) {
    androidx.compose.material3.CenterAlignedTopAppBar(
        title = {
            // 中央导航菜单
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 首页推荐
                NavMenuItem(
                    label = "推荐",
                    isSelected = selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )

                // 排行榜
                NavMenuItem(
                    label = "排行",
                    isSelected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )

                // 搜索
                NavMenuItem(
                    label = "搜索",
                    isSelected = selectedTab == 2,
                    onClick = { onTabSelected(2) }
                )
            }
        },
        actions = {
            // 右侧登录按钮
            IconButton(
                onClick = onLoginClick,
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = Corners.full
                )
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "登录",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = Corners.md
        )
    )
}

/**
 * 导航菜单项组件
 */
@Composable
private fun NavMenuItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val buttonColors = if (isSelected) {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Button(
        onClick = onClick,
        colors = buttonColors,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .height(40.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}