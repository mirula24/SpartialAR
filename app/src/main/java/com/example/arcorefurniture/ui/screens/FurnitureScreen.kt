package com.example.arcorefurniture.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.arcorefurniture.R
import com.example.arcorefurniture.ui.navigation.ARScreenNav

@Composable
fun FurnitureScreen(
    navController: NavController = rememberNavController(),
    categoryItem: String
) {
    var searchText by remember { mutableStateOf("") }

    // Organized furniture models by category
    val furnitureModelsByCategory = mapOf(
        "SOFAS" to mapOf(
            "Brown Sofa" to "models/sofas/sofa_03_1k.gltf/sofa_03_1k.gltf",
            "Black Sofa" to "models/sofas/Sofa_01_1k.gltf/Sofa_01_1k.gltf"
        ),
        "TABLES" to mapOf(
            "Coffee Table" to "models/tables/coffee_table_round_01_1k.gltf/coffee_table_round_01_1k.gltf",
        ),
        "CABINETS" to mapOf(
            "Chinese Cabinet" to "models/tables/chinese_cabinet_1k.gltf/chinese_cabinet_1k.gltf",
            "Modern Wooden Cabinet" to "models/tables/modern_wooden_cabinet_1k.gltf/modern_wooden_cabinet_1k.gltf"
        )
    )

    // Get the furniture items for the selected category
    val furnitureItems = furnitureModelsByCategory[categoryItem]?.keys?.toList() ?: emptyList()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        // Main Content Card
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Search",
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                TextField(
                    value = searchText,
                    onValueChange = { text ->
                        searchText = text
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text("Search...") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = {
                        if (searchText.isNotEmpty()) {
                            IconButton(onClick = {
                                searchText = ""
                            }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Clear search")
                            }
                        }
                    }
                )

                Text(
                    text = categoryItem,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Filter furniture items based on search text
                val filteredFurniture = furnitureItems.filter { it.contains(searchText, ignoreCase = true) }

                // Furniture Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredFurniture) { item ->
                        FurnitureCard(
                            name = item,
                            onAddClick = {
                                val modelPath = furnitureModelsByCategory[categoryItem]?.get(item) ?: ""
                                navController.navigate(ARScreenNav(modelPath))
                            }
                        )
                    }
                }
            }
        }

        // Back Button
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(16.dp)
                .size(48.dp)
                .clip(CircleShape)
                .align(Alignment.TopEnd)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(100.dp))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun FurnitureCard(
    name: String,
    onAddClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Add Button
            IconButton(
                onClick = onAddClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .scale(0.6f)
                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(100.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add to AR",
                    tint = Color(0xFF00B8D4),
                    modifier = Modifier.size(20.dp),
                )
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                // Furniture Image
                Image(
                    painter = painterResource(id = R.drawable.furniture),
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Furniture Name
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
        }
    }
}