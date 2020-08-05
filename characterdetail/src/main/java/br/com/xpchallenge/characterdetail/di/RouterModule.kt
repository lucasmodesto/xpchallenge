package br.com.xpchallenge.characterdetail.di

import br.com.xpchallenge.characterdetail.router.CharacterDetailRouter
import br.com.xpchallenge.di.CharacterDetailRoute
import br.com.xpchallenge.router.CharacterDetailRouteData
import br.com.xpchallenge.router.IRoute
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class RouterModule {

    @Provides
    @CharacterDetailRoute
    fun bindCharacterDetailRoute(): IRoute<CharacterDetailRouteData> {
        return CharacterDetailRouter()
    }
}
