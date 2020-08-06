package br.com.xpchallenge.data.mapper

import br.com.xpchallenge.data.local.room.model.CharacterDBModel
import br.com.xpchallenge.domain.entity.Character
import javax.inject.Inject

class RoomCharacterEntityMapper @Inject constructor() : ICharacterEntityMapper<CharacterDBModel> {

    override fun map(data: CharacterDBModel, isFavorite: Boolean): Character {
        return Character(
            id = data.id,
            name = data.name,
            imageUrl = data.imageUrl,
            description = data.description,
            isFavorite = data.isFavorite,
            isImageAvailable = data.isImageAvailable
        )
    }
}
