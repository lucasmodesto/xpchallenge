package br.com.xpchallenge.characterdetail.di

import br.com.xpchallenge.characterdetail.CharacterDetailContract
import br.com.xpchallenge.characterdetail.CharacterDetailPresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class PresenterModule {

    @Binds
    abstract fun bindDetailPresenter(presenterImpl: CharacterDetailPresenter): CharacterDetailContract.Presenter

}