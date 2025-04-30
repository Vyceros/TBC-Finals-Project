package ge.fitness.workout.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ge.fitness.core.presentation.design_system.component.AppPreview
import ge.fitness.core.presentation.design_system.theme.MomentumTheme
import ge.fitness.workout.presentation.R
import ge.fitness.workout.presentation.model.ArticleListUiModel
import ge.fitness.workout.presentation.model.ArticleUiModel
import ge.fitness.workout.presentation.model.ExerciseUiModel


@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = hiltViewModel(),
    onExerciseClick: (ExerciseUiModel) -> Unit
) {
    val state = viewModel.state

    HomeScreen(
        recommendations = state.exercises,
        onExerciseClick = onExerciseClick,
        onArticleClick = {},
        state = viewModel.state
    )
}

@Composable
fun HomeScreen(
    recommendations: List<ExerciseUiModel>,
    onExerciseClick: (ExerciseUiModel) -> Unit,
    onArticleClick : (List<ArticleListUiModel>) -> Unit,
    state: HomeState
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(bottom = 16.dp)
    ) {
        SectionTitle(
            title = stringResource(R.string.check_out_the_catalog),
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.exercises) { exercise ->
                ExerciseRecommendationCard(
                    exercise = exercise,
                    onClick = { onExerciseClick(exercise) }
                )
            }
        }

        SectionTitle(
            title = stringResource(R.string.top_workout),
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 16.dp)
        )
        state.topWorkout?.let {
            WeeklyChallengeCard(
                exercise = it,
                onClick = {}
            )
        }

    }
}

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = modifier
    )
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ExerciseRecommendationCard(
    exercise: ExerciseUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = modifier
            .width(160.dp)
            .height(160.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(36.dp)
                )
            } else {
                GlideImage(
                    model = exercise.image,
                    contentDescription = exercise.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp)),
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = exercise.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        color = colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                }
            }

        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WeeklyChallengeCard(
    exercise: ExerciseUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    Text(
                        text = "We\nRecommend",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        lineHeight = 34.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = exercise.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color.White)
            ) {
                GlideImage(
                    model = exercise.image,
                    contentDescription = exercise.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@AppPreview
@Composable
fun ExerciseCardPreview() {

    MomentumTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
        ) {
            ExerciseRecommendationCard(
                exercise = ExerciseUiModel(
                    name = "",
                    image = ge.fitness.core.presentation.design_system.R.drawable.ic_time.toString()

                ),
                onClick = {

                }
            )
        }
    }
}