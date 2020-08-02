package br.com.xpchallenge.domain.repository

import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.domain.entity.GetCharacterResultEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface ICharacterRepository {

    fun getCharacters(name: String? = null, paginationOffset: Int? = null): Single<GetCharacterResultEntity>

    fun getFavoriteCharacters(): Observable<List<Character>>

    fun updateFavorite(character: Character): Completable

}