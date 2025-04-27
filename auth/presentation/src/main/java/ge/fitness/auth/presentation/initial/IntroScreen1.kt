package ge.fitness.auth.presentation.initial

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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ge.fitness.auth.presentation.utils.getVectorFromString
import ge.fitness.core.presentation.design_system.component.AppPreview
import ge.fitness.core.presentation.design_system.component.IntroSlideItem
import ge.fitness.core.presentation.design_system.component.MomentumCard
import ge.fitness.core.presentation.design_system.icon.RunningManIcon
import ge.fitness.core.presentation.design_system.theme.MomentumShapes
import kotlinx.coroutines.launch


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
                    text = "Start Your Journey Towards A More Active Lifestyle",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}
val introSlides = listOf(
    PagerItem(
        icon = "Running",
        title = "Start Your Journey Towards A More Active Lifestyle",
        backgroundImageRes = ge.fitness.core.presentation.design_system.R.drawable.ic_back_button
    ),
    PagerItem(
        icon = "Nutrition",
        title = "Learn About Proper Nutrition And Healthy Habits",
        backgroundImageRes = ge.fitness.core.presentation.design_system.R.drawable.ic_back_button
    ),
    PagerItem(
        icon = "Community",
        title = "Join Our Community And Track Your Progress",
        backgroundImageRes = ge.fitness.core.presentation.design_system.R.drawable.ic_back_button,
        isLastSlide = true
    )
)
@Composable
fun IntroPager(
    onButtonClick : () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { introSlides.size })
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {


        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            IntroSlideItem(
                title = introSlides[page].title,
                imageVector = getVectorFromString(introSlides[page].icon),
                backgroundImage = introSlides[page].backgroundImageRes ?: 0,
                isLastSlide = introSlides[page].isLastSlide,
                onButtonClick = {
                    if(page < introSlides.size - 1){
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page + 1)
                        }
                    }else{
                        onButtonClick()
                    }
                }
            )

        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(introSlides.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                }

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
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