package com.kiran.animeapp.ui.screens.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen() {
    val features = listOf(
        Feature("Modern UI", "Built entirely with Jetpack Compose and Material 3.", Icons.Default.PhoneAndroid),
        Feature("Offline First", "Browse cached data seamlessly without an internet connection using Room.", Icons.Default.CloudOff),
        Feature("Pagination", "Infinitely scroll through anime lists with the Paging 3 library.", Icons.Default.SwapVert),
        Feature("Efficient Search", "Instantly find anime with a debounced search bar.", Icons.Default.Search),
        Feature("Favorites System", "Save your favorite anime and view them in a dedicated offline-ready list.", Icons.Default.Favorite),
        Feature("Clean Architecture", "Follows MVVM architecture with Dagger-Hilt for dependency injection.", Icons.Default.AccountTree),
        Feature("Advanced UI", "Features shimmer loading effects and smooth animations for a professional feel.", Icons.Default.AutoAwesome)
    )

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "App Features",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(features) { feature ->
            FeatureCard(feature = feature)
        }
    }
}

@Composable
fun FeatureCard(feature: Feature) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = feature.icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = feature.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = feature.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

data class Feature(val title: String, val description: String, val icon: ImageVector)