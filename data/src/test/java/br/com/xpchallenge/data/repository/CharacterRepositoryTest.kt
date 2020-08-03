package br.com.xpchallenge.data.repository

import br.com.xpchallenge.data.local.room.dao.ICharacterDAO
import br.com.xpchallenge.data.local.room.model.CharacterDBModel
import br.com.xpchallenge.data.mapper.ICharacterEntityMapper
import br.com.xpchallenge.data.mapper.IComicEntityMapper
import br.com.xpchallenge.data.mapper.ISeriesEntityMapper
import br.com.xpchallenge.data.remote.model.CharactersResponse
import br.com.xpchallenge.data.remote.model.ComicsResponse
import br.com.xpchallenge.data.remote.model.SeriesResponse
import br.com.xpchallenge.data.remote.service.IMarvelService
import br.com.xpchallenge.domain.entity.Character
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
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

    @MockK
    lateinit var characterRoomMapper: ICharacterEntityMapper<CharacterDBModel>

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        repository = CharacterRepository(
            service = service,
            characterDao = dao,
            seriesMapper = seriesMapper,
            characterResponseMapper = characterMapper,
            characterRoomMapper = characterRoomMapper,
            comicsMapper = comicsMapper
        )
    }

    @Test
    fun `get favorites success scenario`() {
        val roomModel = mockk<CharacterDBModel>()
        val character = mockk<Character>()
        val data = listOf(roomModel)
        every { dao.observeCharacters() } returns Observable.just(data)
        every { characterRoomMapper.map(roomModel) } returns character

        repository.getFavoriteCharacters()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue {
                it.size == 1 && it[0] == character
            }

        verify {
            dao.observeCharacters()
        }
    }

    @Test
    fun `get favorites error scenario`() {
        val error = Exception()
        every { dao.observeCharacters() } returns Observable.error(error)

        repository.getFavoriteCharacters()
            .test()
            .assertNoValues()
            .assertError(error)

        verify {
            dao.observeCharacters()
        }
    }

    @Test
    fun `load characters success scenario`() {
        val search = "search"
        val charactersResponseMock = CharactersResponse(
            data = CharactersResponse.Data(
                offset = 0,
                total = 0,
                count = 0,
                limit = 20,
                results = listOf(
                    CharactersResponse.Data.CharacterResponse(
                        id = 2134,
                        name = "name",
                        thumbnail = CharactersResponse.Data.CharacterResponse.Thumbnail(
                            extension = ".jpg",
                            path = ""
                        ),
                        events = mockk(),
                        description = "",
                        modified = "",
                        resourceURI = "",
                        series = mockk(),
                        comics = mockk(),
                        stories = mockk(),
                        urls = mockk()
                    )
                )
            ),
            attributionHTML = "",
            etag = "",
            attributionText = "",
            status = "",
            code = 0,
            copyright = ""
        )

        every {
            service.getCharacters(
                search = search,
                offset = any(),
                limit = any(),
                orderBy = any()
            )
        } returns Single.just(
            charactersResponseMock
        )
        val favoritesMock = mockk<CharacterDBModel>(relaxed = true)
        every { dao.getCharacters() } returns Single.just(listOf(favoritesMock))
        every { characterMapper.map(any()) } returns mockk(relaxed = true)


        repository.getCharacters(search)
            .test()
            .assertNoErrors()
            .assertComplete()

        verify {
            service.getCharacters(search)
            dao.getCharacters()
        }
    }

    @Test
    fun `load characters error scenario`() {
        val error = Exception()
        every {
            service.getCharacters(
                search = any(),
                offset = any(),
                limit = any(),
                orderBy = any()
            )
        } returns Single.error(error)

        every { dao.getCharacters() } returns Single.just(listOf())

        repository.getCharacters()
            .test()
            .assertNoValues()
            .assertError(error)

        verify {
            service.getCharacters()
        }
    }

    @Test
    fun `insert favorite success scenario`() {
        every { dao.insert(any()) } returns Completable.complete()
        val character = mockk<Character>(relaxed = true) {
            every { isFavorite } returns true
        }

        repository.updateFavorite(character)
            .test()
            .assertNoErrors()
            .assertComplete()

        verify {
            dao.insert(any())
        }
    }

    @Test
    fun `insert favorite error scenario`() {
        val error = Exception()
        every { dao.insert(any()) } returns Completable.error(error)
        val character = mockk<Character>(relaxed = true) {
            every { isFavorite } returns true
        }

        repository.updateFavorite(character)
            .test()
            .assertNotComplete()
            .assertError(error)

        verify {
            dao.insert(any())
        }
    }

    @Test
    fun `delete favorite success scenario`() {
        every { dao.deleteById(any()) } returns Completable.complete()
        val character = mockk<Character>(relaxed = true) {
            every { isFavorite } returns false
        }

        repository.updateFavorite(character)
            .test()
            .assertNoErrors()
            .assertComplete()

        verify {
            dao.deleteById(any())
        }
    }

    @Test
    fun `delete favorite error scenario`() {
        val error = Exception()
        every { dao.deleteById(any()) } returns Completable.error(error)
        val character = mockk<Character>(relaxed = true) {
            every { isFavorite } returns false
        }

        repository.updateFavorite(character)
            .test()
            .assertNotComplete()
            .assertError(error)

        verify {
            dao.deleteById(any())
        }
    }

    @Test
    fun `get comics success scenario`() {
        val data = mockk<ComicsResponse>(relaxed = true) {
            every { data.results } returns listOf(
                mockk(relaxed = true)
            )
        }

        every { service.getComics(any()) } returns Single.just(data)
        every { comicsMapper.map(any()) } returns mockk(relaxed = true)

        repository.getComics(1)
            .test()
            .assertComplete()
            .assertNoErrors()

        verify {
            service.getComics(any())
            comicsMapper.map(any())
        }
    }

    @Test
    fun `get comics error scenario`() {
        val error = Exception()
        every { service.getComics(any()) } returns Single.error(error)

        repository.getComics(1)
            .test()
            .assertNoValues()
            .assertError(error)

        verify {
            service.getComics(any())
        }
    }

    @Test
    fun `get series success scenario`() {
        val data = mockk<SeriesResponse>(relaxed = true) {
            every { data.results } returns listOf(
                mockk(relaxed = true)
            )
        }

        every { service.getSeries(any()) } returns Single.just(data)
        every { seriesMapper.map(any()) } returns mockk(relaxed = true)

        repository.getSeries(1)
            .test()
            .assertComplete()
            .assertNoErrors()

        verify {
            service.getSeries(any())
            seriesMapper.map(any())
        }
    }

    @Test
    fun `get series error scenario`() {
        val error = Exception()
        every { service.getSeries(any()) } returns Single.error(error)

        repository.getSeries(1)
            .test()
            .assertNoValues()
            .assertError(error)

        verify {
            service.getSeries(any())
        }
    }

}