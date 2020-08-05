package br.com.xpchallenge.data.di

import br.com.xpchallenge.data.BuildConfig
import br.com.xpchallenge.data.remote.NetworkClientProvider
import br.com.xpchallenge.data.remote.interceptor.AuthenticationInterceptor
import br.com.xpchallenge.data.remote.service.IMarvelService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    fun provideAuthInterceptor(): AuthenticationInterceptor {
        return AuthenticationInterceptor()
    }

    @Provides
    fun providesOkHttpClient(interceptor: AuthenticationInterceptor): OkHttpClient {
        return NetworkClientProvider.providesOkHttpClient(authInterceptor = interceptor)
    }

    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return NetworkClientProvider.providesRetrofit(
            baseUrl = BuildConfig.MARVEL_API_URL,
            client = client
        )
    }

    @Provides
    fun providesMarvelService(retrofit: Retrofit): IMarvelService {
        return retrofit.create(IMarvelService::class.java)
    }
}
