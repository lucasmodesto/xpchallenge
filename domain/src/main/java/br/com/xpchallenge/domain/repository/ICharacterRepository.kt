package br.com.xpchallenge.domain.repository

import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.domain.entity.Comic
import br.com.xpchallenge.domain.entity.CharactersResult
import br.com.xpchallenge.domain.entity.Series
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface ICharacterRepository {

    fun getCharacters(
        name: String? = null,
        paginationOffset: Int? = null
    ): Single<CharactersResult>

    fun getFavoriteCharacters(): Observable<List<Character>>

    fun updateFavorite(character: Character): Completable

    fun getComics(id: Int): Single<List<Comic>>

    fun getSeries(id: Int): Single<List<Series>>

}