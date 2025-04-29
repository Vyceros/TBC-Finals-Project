package ge.fitness.core.presentation.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ge.fitness.core.presentation.design_system.icon.CommunityIcon
import ge.fitness.core.presentation.design_system.icon.DumbbellIcon
import ge.fitness.core.presentation.design_system.icon.NutritionIcon
import ge.fitness.core.presentation.design_system.icon.ProfileIcon
import ge.fitness.core.presentation.design_system.icon.NotificationsIcon
import ge.fitness.core.presentation.design_system.icon.RunningManIcon
import ge.fitness.core.presentation.design_system.icon.SearchIcon
import ge.fitness.core.presentation.design_system.theme.MomentumPurple
import ge.fitness.core.presentation.design_system.theme.MomentumSpacing
import ge.fitness.core.presentation.design_system.theme.MomentumTheme

@Composable
fun HomeScreen(
    userName: String = "Luka",
    onSearchClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onWorkoutClick: () -> Unit = {},
    onProgressClick: () -> Unit = {},
    onNutritionClick: () -> Unit = {},
    onCommunityClick: () -> Unit = {},
    onSeeAllRecommendationsClick: () -> Unit = {},
    onRecommendationClick: (String) -> Unit = {},
    onWeeklyChallengeClick: () -> Unit = {},
    onArticleClick: (String) -> Unit = {},
    onBottomNavItemSelected: (Int) -> Unit = {}
) {
    var selectedBottomNavIndex by remember { mutableStateOf(0) }

    MomentumScaffold(
        topAppBar = {},
        floatingActionButton = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header with action icons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MomentumSpacing.medium, vertical = MomentumSpacing.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User greeting
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Hi, $userName",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MomentumPurple
                    )

                    Text(
                        text = "It's time to challenge your limits.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Action icons
                HeaderIconButton(
                    icon = SearchIcon,
                    contentDescription = "Search",
                    onClick = onSearchClick
                )

                Spacer(modifier = Modifier.width(16.dp))

                HeaderIconButton(
                    icon = NotificationsIcon,
                    contentDescription = "Notifications",
                    onClick = onNotificationsClick
                )

                Spacer(modifier = Modifier.width(16.dp))

                HeaderIconButton(
                    icon = ProfileIcon,
                    contentDescription = "Profile",
                    onClick = onProfileClick
                )
            }

            Spacer(modifier = Modifier.height(MomentumSpacing.medium))

            // Main Categories
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MomentumSpacing.medium)
            ) {
                CategoryIconCard(
                    icon = DumbbellIcon,
                    title = "Workout",
                    onClick = onWorkoutClick,
                    modifier = Modifier.weight(1f)
                )

                CategoryIconCard(
                    icon = RunningManIcon, // Replace with progress tracking icon
                    title = "Progress\nTracking",
                    onClick = onProgressClick,
                    modifier = Modifier.weight(1f)
                )

                CategoryIconCard(
                    icon = NutritionIcon,
                    title = "Nutrition",
                    onClick = onNutritionClick,
                    modifier = Modifier.weight(1f)
                )

                CategoryIconCard(
                    icon = CommunityIcon,
                    title = "Community",
                    onClick = onCommunityClick,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(MomentumSpacing.medium))

            // Recommendations Section
            SectionHeader(
                title = "Recommendations",
                showSeeAll = true,
                onSeeAllClick = onSeeAllRecommendationsClick
            )

            // Recommendations Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MomentumSpacing.medium)
            ) {
                // Placeholder images - would use actual resource IDs
                val placeholderImageId = android.R.drawable.ic_menu_gallery

                RecommendationCard(
                    imageResId = placeholderImageId,
                    title = "Squat Exercise",
                    duration = "12 Minutes",
                    calories = "120 kcal",
                    isFavorite = true,
                    onClick = { onRecommendationClick("Squat Exercise") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(MomentumSpacing.small))

                RecommendationCard(
                    imageResId = placeholderImageId,
                    title = "Full Body Stretching",
                    duration = "12 Minutes",
                    calories = "120 kcal",
                    isFavorite = false,
                    onClick = { onRecommendationClick("Full Body Stretching") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(MomentumSpacing.medium))

            // Weekly Challenge
            WeeklyChallengeCard(
                imageResId = android.R.drawable.ic_menu_gallery, // Placeholder
                title = "Weekly Challenge",
                subtitle = "Plank With Hip Twist",
                onClick = onWeeklyChallengeClick,
                modifier = Modifier.padding(horizontal = MomentumSpacing.medium)
            )

            Spacer(modifier = Modifier.height(MomentumSpacing.medium))

            // Articles & Tips
            SectionHeader(title = "Articles & Tips", showSeeAll = false)

            // Article cards
            ArticleCard(
                imageResId = android.R.drawable.ic_menu_gallery, // Placeholder
                title = "Supplement Guide: What You Need To Know",
                isFavorite = true,
                onClick = { onArticleClick("Supplement Guide") },
                modifier = Modifier.padding(horizontal = MomentumSpacing.medium)
            )

            Spacer(modifier = Modifier.height(MomentumSpacing.small))

            ArticleCard(
                imageResId = android.R.drawable.ic_menu_gallery, // Placeholder
                title = "15 Quick & Effective Daily Routines",
                isFavorite = false,
                onClick = { onArticleClick("Daily Routines") },
                modifier = Modifier.padding(horizontal = MomentumSpacing.medium)
            )

            // Bottom spacing to ensure content isn't cut off by navigation bar
            Spacer(modifier = Modifier.height(80.dp))
        }

        // Bottom Navigation
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            MomentumBottomNavigationBar(
                selectedIndex = selectedBottomNavIndex,
                onItemSelected = { index ->
                    selectedBottomNavIndex = index
                    onBottomNavItemSelected(index)
                }
            )
        }
    }
}

@Composable
fun HeaderIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(24.dp)
        )
    }
}

@AppPreview
@Composable
fun HomeScreenPreview() {
    MomentumTheme {
        Surface {
            HomeScreen()
        }
    }
}