package br.com.xpchallenge.domain.repository

import br.com.xpchallenge.domain.entity.Character
import io.reactivex.rxjava3.core.Single

interface ICharacterRepository {

    fun getCharacters(name: String? = null, paginationOffset: Int? = null): Single<List<Character>>

}