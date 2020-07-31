package br.com.xpchallenge.home.di

import br.com.xpchallenge.domain.repository.ICharacterRepository
import br.com.xpchallenge.home.HomePresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class HomeModule {

    @Provides
    fun providesHomePresenter(repository: ICharacterRepository): HomePresenter {
        return HomePresenter(repository = repository)
    }
}