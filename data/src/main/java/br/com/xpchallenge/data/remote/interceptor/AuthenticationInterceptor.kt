package br.com.xpchallenge.data.remote.interceptor

import br.com.xpchallenge.data.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    companion object {
        private const val PUBLIC_KEY_QUERY_PARAM = "api_key"
        private const val PRIVATE_KEY_QUERY_PARAM = "hash"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter(PUBLIC_KEY_QUERY_PARAM, BuildConfig.API_PUBLIC_KEY)
            .addQueryParameter(PRIVATE_KEY_QUERY_PARAM, BuildConfig.API_PRIVATE_KEY)
            .build()

        val request = chain.request().newBuilder().url(url).build()

        return chain.proceed(request)
    }
}