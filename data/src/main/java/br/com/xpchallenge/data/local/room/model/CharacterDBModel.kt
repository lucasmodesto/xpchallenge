package br.com.xpchallenge.data.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterDBModel(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image") val imageUrl: String,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean,
    @ColumnInfo(name = "isImageAvailable") val isImageAvailable: Boolean
)