package br.com.xpchallenge.home.di

import br.com.xpchallenge.home.favorites.FavoriteCharactersContract
import br.com.xpchallenge.home.favorites.FavoriteCharactersPresenter
import br.com.xpchallenge.home.search.SearchCharactersContract
import br.com.xpchallenge.home.search.SearchCharactersPresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class PresenterModule {

    @Binds
    abstract fun bindSearchCharactersPresenter(presenterImpl: SearchCharactersPresenter): SearchCharactersContract.Presenter

    @Binds
    abstract fun bindFavoriteCharactersPresenter(presenterImpl: FavoriteCharactersPresenter): FavoriteCharactersContract.Presenter
}