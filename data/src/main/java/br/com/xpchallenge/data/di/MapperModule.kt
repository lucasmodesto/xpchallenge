package br.com.xpchallenge.data.di

import br.com.xpchallenge.data.local.room.model.CharacterDBModel
import br.com.xpchallenge.data.mapper.CharacterEntityMapper
import br.com.xpchallenge.data.mapper.ICharacterEntityMapper
import br.com.xpchallenge.data.mapper.RoomCharacterEntityMapper
import br.com.xpchallenge.data.mapper.SeriesEntityMapper
import br.com.xpchallenge.data.mapper.ISeriesEntityMapper
import br.com.xpchallenge.data.mapper.IComicEntityMapper
import br.com.xpchallenge.data.mapper.ComicEntityMapper
import br.com.xpchallenge.data.remote.model.CharactersResponse
import br.com.xpchallenge.data.remote.model.ComicsResponse
import br.com.xpchallenge.data.remote.model.SeriesResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class MapperModule {

    @Binds
    abstract fun bindCharacterMapper(mapper: CharacterEntityMapper): ICharacterEntityMapper<CharactersResponse.Data.CharacterResponse>

    @Binds
    abstract fun bindCharacterDbModelMapper(mapper: RoomCharacterEntityMapper): ICharacterEntityMapper<CharacterDBModel>

    @Binds
    abstract fun bindSeriesMapper(mapper: SeriesEntityMapper): ISeriesEntityMapper<SeriesResponse.Data.Result>

    @Binds
    abstract fun bindComicsMapper(mapper: ComicEntityMapper): IComicEntityMapper<ComicsResponse.Data.ComicResponse>
}
