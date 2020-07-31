package br.com.xpchallenge.home.di

import br.com.xpchallenge.di.HomeRoute
import br.com.xpchallenge.home.router.HomeRouter
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
    @HomeRoute
    abstract fun bindHomeRoute(route: HomeRouter): IRoute<RouteData.WithoutInput>

}