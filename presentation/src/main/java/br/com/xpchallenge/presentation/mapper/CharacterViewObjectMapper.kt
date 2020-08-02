package br.com.xpchallenge.presentation.mapper

import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.presentation.CharacterViewObject
import javax.inject.Inject

class CharacterViewObjectMapper @Inject constructor() : ICharacterViewObjectMapper<Character> {

    override fun map(characterEntity: Character): CharacterViewObject {
        with(characterEntity) {
            return CharacterViewObject(
                id = id,
                name = name,
                imageUrl = imageUrl,
                isFavorite = isFavorite,
                isImageAvailable = isImageAvailable,
                description = description
            )
        }
    }
}