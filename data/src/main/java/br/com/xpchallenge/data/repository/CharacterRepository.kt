package br.com.xpchallenge.data.repository

import br.com.xpchallenge.data.CharactersResponse
import br.com.xpchallenge.data.local.CharacterDBModel
import br.com.xpchallenge.data.local.dao.ICharacterDAO
import br.com.xpchallenge.data.mapper.ICharacterEntityMapper
import br.com.xpchallenge.data.remote.service.IMarvelService
import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.domain.repository.ICharacterRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val service: IMarvelService,
    private val characterDao: ICharacterDAO,
    private val mapper: ICharacterEntityMapper<CharactersResponse.Data.CharacterResponse>
) : ICharacterRepository {

    override fun getCharacters(name: String?, paginationOffset: Int?): Single<List<Character>> {
        return Single.zip(
            service.getCharacters(name = name, offset = paginationOffset),
            characterDao.getCharacters(),
            BiFunction { apiResponse, favoriteCharacters ->
                val characters = apiResponse.data.results.map { characterResponse ->
                    mapper.map(
                        data = characterResponse,
                        isFavorite = favoriteCharacters.any { it.id == characterResponse.id })
                }
                characters
            })
    }

    override fun getFavoriteCharacters(): Single<List<Character>> {
        return characterDao.getCharacters().map {
            it.map { characterDbModel ->
                Character(
                    id = characterDbModel.id,
                    name = characterDbModel.name,
                    imageUrl = characterDbModel.imageUrl,
                    description = characterDbModel.description,
                    isFavorite = characterDbModel.isFavorite
                )
            }
        }
    }

    override fun updateFavorite(character: Character): Completable {
        return if (character.isFavorite) {
            characterDao.insert(
                CharacterDBModel(
                    id = character.id,
                    name = character.name,
                    description = character.description,
                    imageUrl = character.imageUrl,
                    isFavorite = character.isFavorite
                )
            )
        } else {
            characterDao.deleteById(character.id)
        }
    }
}