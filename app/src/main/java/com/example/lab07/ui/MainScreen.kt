package com.example.lab07.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lab07.data.DataSource
import com.example.lab07.model.ItemModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var selectedItem by remember { mutableStateOf<ItemModel?>(null) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Lab 07 - LazyColumn",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Catálogo de Productos (${DataSource.items.size} registros)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                items = DataSource.items,
                key = { it.id }
            ) { item ->
                ItemCard(
                    item = item,
                    onClick = {
                        selectedItem = item
                    }
                )
            }
        }

        selectedItem = selectedItem?.let { item ->
            DetailDialog(
                item = item,
                onDismiss = { selectedItem = null }
            )
            item
        }
    }
}
