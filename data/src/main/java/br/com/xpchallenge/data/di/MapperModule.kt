package br.com.xpchallenge.data.di

import br.com.xpchallenge.data.CharactersResponse
import br.com.xpchallenge.data.mapper.CharacterEntityMapper
import br.com.xpchallenge.data.mapper.ICharacterEntityMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class MapperModule {

    @Binds
    abstract fun bindCharacterMapper(mapper: CharacterEntityMapper): ICharacterEntityMapper<CharactersResponse.Data.CharacterResponse>
}