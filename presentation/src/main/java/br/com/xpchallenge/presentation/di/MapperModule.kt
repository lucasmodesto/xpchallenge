package br.com.xpchallenge.presentation.di

import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.domain.entity.Comic
import br.com.xpchallenge.domain.entity.Series
import br.com.xpchallenge.presentation.mapper.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class MapperModule {

    @Binds
    abstract fun bindCharacterViewObjectMapper(mapperImpl: CharacterViewObjectMapper): ICharacterViewObjectMapper<Character>

    @Binds
    abstract fun bindComicsViewObjectMapper(mapperImpl: ComicViewObjectMapper): IMediaViewObjectMapper<Comic>

    @Binds
    abstract fun bindSeriesViewObjectMapper(mapperImpl: SeriesViewObjectMapper): IMediaViewObjectMapper<Series>
}