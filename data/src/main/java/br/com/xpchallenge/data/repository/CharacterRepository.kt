package br.com.xpchallenge.data.repository

import br.com.xpchallenge.data.remote.service.IMarvelService
import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.domain.repository.ICharacterRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

open class CharacterRepository @Inject constructor(val service: IMarvelService) :
    ICharacterRepository {

    override fun getCharacters(name: String, paginationOffset: Int): Single<List<Character>> {
        return service.getCharacters(name = name, offset = paginationOffset).map {
            listOf<Character>() // TODO:
        }
    }
}