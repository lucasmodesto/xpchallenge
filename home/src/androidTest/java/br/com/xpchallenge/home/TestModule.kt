package br.com.xpchallenge.home

import br.com.xpchallenge.di.CharacterDetailRoute
import br.com.xpchallenge.router.CharacterDetailRouteData
import br.com.xpchallenge.router.IRoute
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.mockk.mockk

@Module
@InstallIn(ActivityComponent::class)
class TestModule {

    @CharacterDetailRoute
    @Provides
    fun bindAnalyticsService(): IRoute<CharacterDetailRouteData> {
        return mockk()
    }
}