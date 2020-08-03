package br.com.xpchallenge.presentation.model

import android.os.Parcelable
import br.com.xpchallenge.domain.entity.Character
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterViewObject(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val isImageAvailable: Boolean,
    var isFavorite: Boolean
) : Parcelable {

    fun mapToEntity(): Character {
        return Character(
            id = this.id,
            name = this.name,
            description = this.description,
            imageUrl = this.imageUrl,
            isImageAvailable = this.isImageAvailable,
            isFavorite = this.isFavorite
        )
    }
}