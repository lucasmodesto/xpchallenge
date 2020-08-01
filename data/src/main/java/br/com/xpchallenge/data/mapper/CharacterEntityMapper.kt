package br.com.xpchallenge.data.mapper

import br.com.xpchallenge.data.CharactersResponse
import br.com.xpchallenge.domain.entity.Character
import javax.inject.Inject

class CharacterEntityMapper @Inject constructor() :
    ICharacterEntityMapper<CharactersResponse.Data.CharacterResponse> {

    override fun map(data: CharactersResponse.Data.CharacterResponse): Character {
        return Character(
            id = data.id,
            name = data.name,
            description = data.description,
            imageUrl = formatImageUrl(data.thumbnail),
            isFavorite = false // TODO
        )
    }

    /**
     *  Wondering if this should be domain or presentation logic, but i guess this should be done server-side
     */
    private fun formatImageUrl(thumbnail: CharactersResponse.Data.CharacterResponse.Thumbnail): String {
        return thumbnail.path.replace("http://", "https://") + "." + thumbnail.extension
    }

}