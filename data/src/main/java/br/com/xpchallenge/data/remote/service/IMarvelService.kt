package br.com.xpchallenge.data.remote.service

import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IMarvelService {

    @GET("characters")
    fun getCharacters(
        @Query("name") name: String? = null,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int? = null,
        @Query("orderBy") orderBy: String = "name"
    ): Single<Response<ResponseBody>>

}