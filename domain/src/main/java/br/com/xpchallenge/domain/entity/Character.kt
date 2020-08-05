package br.com.xpchallenge.domain.entity

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    var isFavorite: Boolean,
    val isImageAvailable: Boolean
)
