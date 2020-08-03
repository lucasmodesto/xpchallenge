package br.com.xpchallenge.data.remote.service

import br.com.xpchallenge.data.remote.model.CharactersResponse
import br.com.xpchallenge.data.remote.model.ComicsResponse
import br.com.xpchallenge.data.remote.model.SeriesResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMarvelService {

    @GET("characters")
    fun getCharacters(
        @Query("nameStartsWith") search: String? = null,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int? = null,
        @Query("orderBy") orderBy: String = "name"
    ): Single<CharactersResponse>


    @GET("characters/{characterId}/comics")
    fun getComics(@Path("characterId") characterId: Int): Single<ComicsResponse>

    @GET("characters/{characterId}/series")
    fun getSeries(@Path("characterId") characterId: Int): Single<SeriesResponse>


}