package br.com.xpchallenge.home

import br.com.xpchallenge.presentation.favorite.FavoritePresenter
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class FavoriteCharactersPresenter @Inject constructor() :
    FavoritePresenter<HomeContract.FavoritesView>(),
    HomeContract.FavoritesPresenter
