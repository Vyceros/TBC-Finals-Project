package ge.fitness.auth.presentation.initial

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import ge.fitness.auth.presentation.R
import ge.fitness.auth.presentation.utils.getVectorFromString
import ge.fitness.core.presentation.design_system.component.AppPreview
import ge.fitness.core.presentation.design_system.component.IntroSlideItem
import ge.fitness.core.presentation.design_system.component.MomentumCard
import ge.fitness.core.presentation.design_system.icon.RunningManIcon
import ge.fitness.core.presentation.design_system.theme.MomentumShapes
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


@Composable
fun ActiveLifestyleBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clip(MomentumShapes.medium)
            .padding(vertical = 24.dp),
    ) {
        MomentumCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = RunningManIcon,
                    contentDescription = "Runner Icon",
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.start_your_journey_towards_a_more_active_lifestyle),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun IntroPager(
    onButtonClick : () -> Unit
) {
    val introSlides = listOf(
        PagerItem(
            icon = "Running",
            title = stringResource(R.string.start_your_journey_towards_a_more_active_lifestyle),
            backgroundImageRes = ge.fitness.core.presentation.design_system.R.drawable.ic_back_button
        ),
        PagerItem(
            icon = "Nutrition",
            title = stringResource(R.string.learn_about_proper_nutrition_and_healthy_habits),
            backgroundImageRes = ge.fitness.core.presentation.design_system.R.drawable.ic_back_button
        ),
        PagerItem(
            icon = "Community",
            title = stringResource(R.string.join_our_community_and_track_your_progress),
            backgroundImageRes = ge.fitness.core.presentation.design_system.R.drawable.ic_back_button,
            isLastSlide = true
        )
    )

    val pagerState = rememberPagerState(pageCount = { introSlides.size })
    val coroutineScope = rememberCoroutineScope()

    val pagerFlingBehavior = PagerDefaults.flingBehavior(
        state = pagerState,
    )

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            flingBehavior = pagerFlingBehavior
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                IntroSlideItem(
                    title = introSlides[page].title,
                    imageVector = getVectorFromString(introSlides[page].icon),
                    backgroundImage = introSlides[page].backgroundImageRes ?: 0,
                    isLastSlide = introSlides[page].isLastSlide,
                    onButtonClick = {
                        if(page < introSlides.size - 1){
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    page = page + 1,
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }
                        } else {
                            onButtonClick()
                        }
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(introSlides.size) { iteration ->
                val isSelected = pagerState.currentPage == iteration
                val color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                }

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(if (isSelected) 12.dp else 10.dp)
                        .scale(
                            if (isSelected) 1f else 0.8f
                        )
                )
            }
        }
    }
}


@Composable
@AppPreview
fun IntroPreview() {
    IntroPager {
    }
}