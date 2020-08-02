package br.com.xpchallenge.presentation.di

import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.presentation.mapper.CharacterViewObjectMapper
import br.com.xpchallenge.presentation.mapper.ICharacterViewObjectMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class PresentationBindsModule {

    @Binds
    abstract fun bindCharacterViewObjectMapper(mapperImpl: CharacterViewObjectMapper): ICharacterViewObjectMapper<Character>
}