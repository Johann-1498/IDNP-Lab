package com.example.lab07.model

data class ItemModel(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val location: String
)
