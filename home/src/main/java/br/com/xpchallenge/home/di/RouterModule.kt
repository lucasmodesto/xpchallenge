package br.com.xpchallenge.home.di

import br.com.xpchallenge.di.HomeRoute
import br.com.xpchallenge.home.router.HomeRouter
import br.com.xpchallenge.router.HomeRouteData
import br.com.xpchallenge.router.IRoute
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class RouterModule {

    @Provides
    @HomeRoute
    fun bindHomeRoute(): IRoute<HomeRouteData> {
        return HomeRouter()
    }
}
