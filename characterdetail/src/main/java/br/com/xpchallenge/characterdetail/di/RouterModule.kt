package br.com.xpchallenge.characterdetail.di

import br.com.xpchallenge.characterdetail.router.CharacterDetailRouter
import br.com.xpchallenge.di.CharacterDetailRoute
import br.com.xpchallenge.router.IRoute
import br.com.xpchallenge.router.RouteData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class RouterModule {

    @Binds
    @CharacterDetailRoute
    abstract fun bindCharacterDetailRoute(route: CharacterDetailRouter): IRoute<RouteData.CharacterDetailData>
}