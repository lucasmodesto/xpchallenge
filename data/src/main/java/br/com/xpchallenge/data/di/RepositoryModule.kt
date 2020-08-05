package br.com.xpchallenge.data.di

import br.com.xpchallenge.data.repository.CharacterRepository
import br.com.xpchallenge.domain.repository.ICharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCharacterRepository(repositoryImpl: CharacterRepository): ICharacterRepository
}
