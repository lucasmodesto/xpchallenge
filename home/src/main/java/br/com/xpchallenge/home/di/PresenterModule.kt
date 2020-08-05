package br.com.xpchallenge.home.di

import br.com.xpchallenge.home.FavoriteCharactersPresenter
import br.com.xpchallenge.home.HomeContract
import br.com.xpchallenge.home.SearchCharactersPresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class PresenterModule {

    @Binds
    abstract fun bindHomePresenter(presenterImpl: SearchCharactersPresenter): HomeContract.SearchCharactersPresenter

    @Binds
    abstract fun bindFavoritesPresenter(presenterImpl: FavoriteCharactersPresenter): HomeContract.FavoritesPresenter
}
