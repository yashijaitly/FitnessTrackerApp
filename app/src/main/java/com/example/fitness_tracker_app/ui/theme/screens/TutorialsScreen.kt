package com.example.fitness_tracker_app.ui.theme.screens
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fitness_tracker_app.data.database.entities.TutorialVideo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialsScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

    // Mock tutorial videos for demonstration
    val tutorialVideos = remember {
        listOf(
            TutorialVideo(
                id = 1,
                title = "Perfect Push-Up Form",
                description = "Learn the proper technique for push-ups to maximize effectiveness and prevent injury",
                youtubeId = "IODxDxX7oi4",
                category = "Strength",
                duration = "5:32",
                thumbnailUrl = "https://img.youtube.com/vi/IODxDxX7oi4/maxresdefault.jpg",
                viewCount = 1250000,
                rating = 4.8f
            ),
            TutorialVideo(
                id = 2,
                title = "Beginner Yoga Flow",
                description = "A gentle 15-minute yoga sequence perfect for beginners",
                youtubeId = "v7AYKMP6rOE",
                category = "Yoga",
                duration = "15:24",
                thumbnailUrl = "https://img.youtube.com/vi/v7AYKMP6rOE/maxresdefault.jpg",
                viewCount = 892000,
                rating = 4.9f
            ),
            TutorialVideo(
                id = 3,
                title = "HIIT Cardio Workout",
                description = "High-intensity interval training to burn calories and improve cardiovascular health",
                youtubeId = "ml6cT4AZdqI",
                category = "Cardio",
                duration = "20:15",
                thumbnailUrl = "https://img.youtube.com/vi/ml6cT4AZdqI/maxresdefault.jpg",
                viewCount = 567000,
                rating = 4.7f
            ),
            TutorialVideo(
                id = 4,
                title = "Squats: Form and Variations",
                description = "Master the squat movement with proper form and learn different variations",
                youtubeId = "aclHkVaku9U",
                category = "Strength",
                duration = "8:45",
                thumbnailUrl = "https://img.youtube.com/vi/aclHkVaku9U/maxresdefault.jpg",
                viewCount = 234000,
                rating = 4.6f
            ),
            TutorialVideo(
                id = 5,
                title = "Flexibility and Stretching",
                description = "Essential stretches to improve flexibility and reduce muscle tension",
                youtubeId = "L_xrDAtykMI",
                category = "Flexibility",
                duration = "12:30",
                thumbnailUrl = "https://img.youtube.com/vi/L_xrDAtykMI/maxresdefault.jpg",
                viewCount = 445000,
                rating = 4.5f
            )
        )
    }

    val categories = remember { tutorialVideos.map { it.category }.distinct() }
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredVideos = remember(selectedCategory) {
        if (selectedCategory == "All") {
            tutorialVideos
        } else {
            tutorialVideos.filter { it.category == selectedCategory }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Tutorial Videos",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Category Filter
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                FilterChip(
                    onClick = { selectedCategory = "All" },
                    label = { Text("All") },
                    selected = selectedCategory == "All",
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            items(categories) { category ->
                FilterChip(
                    onClick = { selectedCategory = category },
                    label = { Text(category) },
                    selected = selectedCategory == category,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Video List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredVideos) { video ->
                VideoCard(
                    video = video,
                    onVideoClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=${video.youtubeId}"))
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun VideoCard(
    video: TutorialVideo,
    onVideoClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onVideoClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Thumbnail
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = video.thumbnailUrl,
                    contentDescription = video.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Crop
                )

                // Play button overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }

                // Duration badge
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                ) {
                    Text(
                        text = video.duration ?: "",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Video Details
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = video.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = video.description ?: "",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = video.category,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Text(
                        text = "${video.viewCount / 1000}K views",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}