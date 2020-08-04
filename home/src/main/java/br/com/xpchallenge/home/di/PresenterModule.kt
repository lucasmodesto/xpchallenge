package br.com.xpchallenge.home.di

import br.com.xpchallenge.home.HomeContract
import br.com.xpchallenge.home.HomePresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class PresenterModule {

    @Binds
    abstract fun bindHomePresenter(presenterImpl: HomePresenter): HomeContract.Presenter
}