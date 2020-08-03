package br.com.xpchallenge.data

import br.com.xpchallenge.data.local.room.dao.ICharacterDAO
import br.com.xpchallenge.data.local.room.model.CharacterDBModel
import br.com.xpchallenge.data.mapper.ICharacterEntityMapper
import br.com.xpchallenge.data.mapper.IComicEntityMapper
import br.com.xpchallenge.data.mapper.ISeriesEntityMapper
import br.com.xpchallenge.data.remote.model.CharactersResponse
import br.com.xpchallenge.data.remote.model.ComicsResponse
import br.com.xpchallenge.data.remote.model.SeriesResponse
import br.com.xpchallenge.data.remote.service.IMarvelService
import br.com.xpchallenge.data.repository.CharacterRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test

class CharacterRepositoryTest {

    lateinit var repository: CharacterRepository

    @MockK
    lateinit var service: IMarvelService

    @MockK
    lateinit var dao: ICharacterDAO

    @MockK
    lateinit var seriesMapper: ISeriesEntityMapper<SeriesResponse.Data.Result>

    @MockK
    lateinit var comicsMapper: IComicEntityMapper<ComicsResponse.Data.ComicResponse>

    @MockK
    lateinit var characterMapper: ICharacterEntityMapper<CharactersResponse.Data.CharacterResponse>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = CharacterRepository(
            service = service,
            characterDao = dao,
            seriesMapper = seriesMapper,
            charactersMapper = characterMapper,
            comicsMapper = comicsMapper
        )
    }

    @Test
    fun `should load favorite characters from cache`() {
        val data = listOf<CharacterDBModel>(mockk(relaxed = true))
        every { dao.observeCharacters() } returns Observable.just(data)

        repository.getFavoriteCharacters()

        verify {
            dao.observeCharacters()
        }

        repository.getFavoriteCharacters()
            .test()
            .assertNoErrors()
            .assertValue {
                it.all { character -> character.isFavorite }
            }
    }


}