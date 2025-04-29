package ge.fitness.core.presentation.design_system.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ge.fitness.core.presentation.design_system.icon.CaloriesIcon
import ge.fitness.core.presentation.design_system.icon.FavouriteIcon
import ge.fitness.core.presentation.design_system.icon.HomeIcon
import ge.fitness.core.presentation.design_system.icon.NutritionIcon
import ge.fitness.core.presentation.design_system.icon.ResourcesIcon
import ge.fitness.core.presentation.design_system.icon.SeeAllArrowIcon
import ge.fitness.core.presentation.design_system.icon.SupportIcon
import ge.fitness.core.presentation.design_system.icon.TimeIcon
import ge.fitness.core.presentation.design_system.theme.MomentumBlack
import ge.fitness.core.presentation.design_system.theme.MomentumLimeGreen
import ge.fitness.core.presentation.design_system.theme.MomentumPurple
import ge.fitness.core.presentation.design_system.theme.MomentumSpacing
import ge.fitness.core.presentation.design_system.theme.MomentumTheme
import ge.fitness.core.presentation.design_system.theme.MomentumWhite

/**
 * Category icon card used for main navigation items (Workout, Progress Tracking, etc.)
 */
@Composable
fun CategoryIconCard(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MomentumLimeGreen,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MomentumLimeGreen
        )
    }
}


@Composable
fun RecommendationCard(
    modifier: Modifier = Modifier,
    imageResId: Int,
    title: String,
    duration: String,
    calories: String,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(BorderStroke(1.dp, MomentumWhite.copy(alpha = 0.2f)), RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.6f)
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, MomentumBlack.copy(alpha = 0.7f)),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        // Favorite star
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(MomentumWhite.copy(alpha = 0.8f))
                .clickable { onFavoriteClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (isFavorite) MomentumLimeGreen else MomentumWhite
            )
        }

        // Text and info
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MomentumLimeGreen
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = NutritionIcon, //aq chasvi TimeIcon
                    contentDescription = null,
                    tint = MomentumPurple,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = duration,
                    style = MaterialTheme.typography.bodySmall,
                    color = MomentumWhite
                )
                Spacer(Modifier.width(12.dp))
                Icon(
                    imageVector = NutritionIcon, //aq chasvi CaloriesIcon
                    contentDescription = null,
                    tint = MomentumPurple,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = calories,
                    style = MaterialTheme.typography.bodySmall,
                    color = MomentumWhite
                )
            }
        }

        // Play button
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
                .size(36.dp)
                .clip(CircleShape)
                .border(BorderStroke(2.dp, MomentumPurple), CircleShape)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = "Play",
                tint = MomentumPurple,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

/**
 * Weekly challenge card with background color, image and text
 */
@Composable
fun WeeklyChallengeCard(
    modifier: Modifier = Modifier,
    imageResId: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MomentumSpacing.medium)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = MomentumSpacing.small)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MomentumLimeGreen
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .weight(1f)
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium)
            )
        }
    }
}

/**
 * Article card with image and text
 */

private val CardShape = RoundedCornerShape(16.dp)
@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    imageResId: Int,
    title: String,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(160.dp)
            .clip(CardShape)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(CardShape)
            )

            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Star else NutritionIcon,
                    contentDescription = null,
                    tint = if (isFavorite) MomentumLimeGreen else MomentumWhite
                )
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    showSeeAll: Boolean = true,
    onSeeAllClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MomentumSpacing.medium, vertical = MomentumSpacing.small),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MomentumLimeGreen
        )

        if (showSeeAll) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onSeeAllClick() }
            ) {
                Text(
                    text = "See All",
                    style = MaterialTheme.typography.labelMedium,
                    color = MomentumLimeGreen
                )

                Icon(
                    imageVector = NutritionIcon, //aq chasvi SeeAllArrowIcon
                    contentDescription = "See all",
                    tint = MomentumLimeGreen,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

/**
 * Bottom navigation bar
 */
@Composable
fun MomentumBottomNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = MomentumSpacing.small),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavItem(
            selected = selectedIndex == 0,
            icon = HomeIcon, // Replace with home icon
            label = "Home",
            onClick = { onItemSelected(0) }
        )

        BottomNavItem(
            selected = selectedIndex == 1,
            icon = ResourcesIcon, // Replace with appropriate icon
            label = "Resources",
            onClick = { onItemSelected(1) }
        )

        BottomNavItem(
            selected = selectedIndex == 2,
            icon = FavouriteIcon, // Replace with appropriate icon
            label = "Favourites",
            onClick = { onItemSelected(2) }
        )

        BottomNavItem(
            selected = selectedIndex == 3,
            icon = SupportIcon, // Replace with appropriate icon
            label = "Support",
            onClick = { onItemSelected(3) }
        )
    }
}

@Composable
private fun BottomNavItem(
    selected: Boolean,
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) MomentumLimeGreen else MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) MomentumLimeGreen else MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
    }
}

@AppPreview
@Composable
fun CategoryIconCardPreview() {
    MomentumTheme {
        Surface {
            CategoryIconCard(
                icon = NutritionIcon,
                title = "Nutrition",
                onClick = {}
            )
        }
    }
}

@AppPreview
@Composable
fun RecommendationCardPreview() {
    MomentumTheme {
        Surface {
            val placeholderImageId = android.R.drawable.ic_menu_gallery

            RecommendationCard(
                imageResId = placeholderImageId,
                title = "Squat Exercise",
                duration = "12 Minutes",
                calories = "120 kcal",
                isFavorite = true,
                modifier = Modifier.width(200.dp)
            )
        }
    }
}

@AppPreview
@Composable
fun WeeklyChallengeCardPreview() {
    MomentumTheme {
        Surface {
            // Placeholder - would use an actual R.drawable ID in your app
            val placeholderImageId = android.R.drawable.ic_menu_gallery

            WeeklyChallengeCard(
                imageResId = placeholderImageId,
                title = "Weekly Challenge",
                subtitle = "Plank With Hip Twist"
            )
        }
    }
}

@AppPreview
@Composable
fun ArticleCardPreview() {
    MomentumTheme {
        Surface {
            // Placeholder - would use an actual R.drawable ID in your app
            val placeholderImageId = android.R.drawable.ic_menu_gallery

            ArticleCard(
                imageResId = placeholderImageId,
                title = "Supplement Guide: What to Take and When",
                isFavorite = true
            )
        }
    }
}

@AppPreview
@Composable
fun BottomNavigationBarPreview() {
    MomentumTheme {
        Surface {
            MomentumBottomNavigationBar(
                selectedIndex = 0,
                onItemSelected = {}
            )
        }
    }
}