package br.com.xpchallenge.data.repository

import br.com.xpchallenge.data.CharactersResponse
import br.com.xpchallenge.data.mapper.ICharacterEntityMapper
import br.com.xpchallenge.data.remote.service.IMarvelService
import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.domain.repository.ICharacterRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val service: IMarvelService,
    private val mapper: ICharacterEntityMapper<CharactersResponse.Data.CharacterResponse>
) : ICharacterRepository {

    override fun getCharacters(name: String?, paginationOffset: Int?): Single<List<Character>> {
        return service.getCharacters(name = name, offset = paginationOffset).map {
            it.data.results.map { characterResponse ->
                mapper.map(characterResponse)
            }
        }
    }
}