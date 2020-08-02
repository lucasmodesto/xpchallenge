package br.com.xpchallenge.data.repository

import br.com.xpchallenge.data.remote.model.CharactersResponse
import br.com.xpchallenge.data.local.room.dao.ICharacterDAO
import br.com.xpchallenge.data.local.room.model.CharacterDBModel
import br.com.xpchallenge.data.mapper.ICharacterEntityMapper
import br.com.xpchallenge.data.mapper.IComicEntityMapper
import br.com.xpchallenge.data.mapper.ISeriesEntityMapper
import br.com.xpchallenge.data.remote.model.ComicsResponse
import br.com.xpchallenge.data.remote.model.SeriesResponse
import br.com.xpchallenge.data.remote.service.IMarvelService
import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.domain.entity.Comic
import br.com.xpchallenge.domain.entity.GetCharacterResultEntity
import br.com.xpchallenge.domain.entity.Series
import br.com.xpchallenge.domain.repository.ICharacterRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val service: IMarvelService,
    private val characterDao: ICharacterDAO,
    private val charactersMapper: ICharacterEntityMapper<CharactersResponse.Data.CharacterResponse>,
    private val comicsMapper: IComicEntityMapper<ComicsResponse.Data.ComicResponse>,
    private val seriesMapper: ISeriesEntityMapper<SeriesResponse.Data.Result>
) : ICharacterRepository {

    override fun getCharacters(
        name: String?,
        paginationOffset: Int?
    ): Single<GetCharacterResultEntity> {
        val loadCharactersSingleZip: Single<GetCharacterResultEntity> = Single.zip(
            service.getCharacters(name = name, offset = paginationOffset),
            characterDao.getCharacters(),
            BiFunction { apiResponse, favoriteCharacters ->
                val characters = apiResponse.data.results.map { characterResponse ->
                    charactersMapper.map(
                        data = characterResponse,
                        isFavorite = favoriteCharacters.find { it.id == characterResponse.id }?.isFavorite
                            ?: false
                    )
                }
                GetCharacterResultEntity(
                    characters = characters,
                    count = apiResponse.data.count,
                    totalCount = apiResponse.data.total,
                    offset = apiResponse.data.offset
                )
            })

        return loadCharactersSingleZip.flatMap { result ->
            updateCache(result.characters).toSingleDefault(result)
        }
    }

    private fun updateCache(characters: List<Character>): Completable {
        return Observable.fromIterable(characters).flatMapCompletable {
            updateFavorite(it)
        }
    }

    override fun getFavoriteCharacters(): Observable<List<Character>> {
        return characterDao.observeCharacters().map {
            it.map { characterDbModel ->
                Character(
                    id = characterDbModel.id,
                    name = characterDbModel.name,
                    imageUrl = characterDbModel.imageUrl,
                    description = characterDbModel.description,
                    isFavorite = characterDbModel.isFavorite,
                    isImageAvailable = true
                )
            }.filter {
                it.isFavorite
            }
        }
    }

    override fun updateFavorite(character: Character): Completable {
        return characterDao.insert(
            CharacterDBModel(
                id = character.id,
                name = character.name,
                description = character.description,
                imageUrl = character.imageUrl,
                isFavorite = character.isFavorite
            )
        )
    }

    override fun getComics(id: Int): Single<List<Comic>> {
        return service.getComics(id).map {
            it.data.results.map { itemResponse ->
                comicsMapper.map(itemResponse)
            }
        }
    }

    override fun getSeries(id: Int): Single<List<Series>> {
        return service.getSeries(id).map {
            it.data.results.map { itemResponse ->
                seriesMapper.map(itemResponse)
            }
        }
    }
}