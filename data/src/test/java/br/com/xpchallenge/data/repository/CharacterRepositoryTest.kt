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

    private lateinit var repository: CharacterRepository

    @MockK
    lateinit var serviceMock: IMarvelService

    @MockK
    lateinit var daoMock: ICharacterDAO

    @MockK
    lateinit var seriesMapperMock: ISeriesEntityMapper<SeriesResponse.Data.Result>

    @MockK
    lateinit var comicsMapperMock: IComicEntityMapper<ComicsResponse.Data.ComicResponse>

    @MockK
    lateinit var characterMapperMock: ICharacterEntityMapper<CharactersResponse.Data.CharacterResponse>

    @MockK
    lateinit var characterRoomMapperMock: ICharacterEntityMapper<CharacterDBModel>

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        repository = CharacterRepository(
            service = serviceMock,
            characterDao = daoMock,
            seriesMapper = seriesMapperMock,
            characterResponseMapper = characterMapperMock,
            characterRoomMapper = characterRoomMapperMock,
            comicsMapper = comicsMapperMock
        )
    }

    @Test
    fun `get favorites success scenario`() {
        val roomModel = mockk<CharacterDBModel>()
        val character = mockk<Character>()
        val data = listOf(roomModel)
        every { daoMock.observeCharacters() } returns Observable.just(data)
        every { characterRoomMapperMock.map(roomModel) } returns character

        repository.getFavoriteCharacters()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue {
                it.size == 1 && it[0] == character
            }

        verify {
            daoMock.observeCharacters()
        }
    }

    @Test
    fun `get favorites error scenario`() {
        val error = Exception()
        every { daoMock.observeCharacters() } returns Observable.error(error)

        repository.getFavoriteCharacters()
            .test()
            .assertNoValues()
            .assertError(error)

        verify {
            daoMock.observeCharacters()
        }
    }

    @Test
    fun `load characters success scenario`() {
        val query = "search"
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
            serviceMock.getCharacters(
                query = query,
                offset = any(),
                limit = any(),
                orderBy = any()
            )
        } returns Single.just(
            charactersResponseMock
        )
        val favoritesMock = mockk<CharacterDBModel>(relaxed = true)
        every { daoMock.getCharacters() } returns Single.just(listOf(favoritesMock))
        every { characterMapperMock.map(any()) } returns mockk(relaxed = true)


        repository.getCharacters(query, 0)
            .test()
            .assertNoErrors()
            .assertComplete()

        verify {
            serviceMock.getCharacters(query = query, offset = 0)
            daoMock.getCharacters()
        }
    }

    @Test
    fun `load characters error scenario`() {
        val error = Exception()
        every {
            serviceMock.getCharacters(
                query = any(),
                offset = any(),
                limit = any(),
                orderBy = any()
            )
        } returns Single.error(error)

        every { daoMock.getCharacters() } returns Single.just(listOf())

        repository.getCharacters(paginationOffset = 0)
            .test()
            .assertNoValues()
            .assertError(error)

        verify {
            serviceMock.getCharacters(offset = 0)
        }
    }

    @Test
    fun `insert favorite success scenario`() {
        every { daoMock.insert(any()) } returns Completable.complete()
        val character = mockk<Character>(relaxed = true) {
            every { isFavorite } returns true
        }

        repository.updateFavorite(character)
            .test()
            .assertNoErrors()
            .assertComplete()

        verify {
            daoMock.insert(any())
        }
    }

    @Test
    fun `insert favorite error scenario`() {
        val error = Exception()
        every { daoMock.insert(any()) } returns Completable.error(error)
        val character = mockk<Character>(relaxed = true) {
            every { isFavorite } returns true
        }

        repository.updateFavorite(character)
            .test()
            .assertNotComplete()
            .assertError(error)

        verify {
            daoMock.insert(any())
        }
    }

    @Test
    fun `delete favorite success scenario`() {
        every { daoMock.deleteById(any()) } returns Completable.complete()
        val character = mockk<Character>(relaxed = true) {
            every { isFavorite } returns false
        }

        repository.updateFavorite(character)
            .test()
            .assertNoErrors()
            .assertComplete()

        verify {
            daoMock.deleteById(any())
        }
    }

    @Test
    fun `delete favorite error scenario`() {
        val error = Exception()
        every { daoMock.deleteById(any()) } returns Completable.error(error)
        val character = mockk<Character>(relaxed = true) {
            every { isFavorite } returns false
        }

        repository.updateFavorite(character)
            .test()
            .assertNotComplete()
            .assertError(error)

        verify {
            daoMock.deleteById(any())
        }
    }

    @Test
    fun `get comics success scenario`() {
        val data = mockk<ComicsResponse>(relaxed = true) {
            every { data.results } returns listOf(
                mockk(relaxed = true)
            )
        }

        every { serviceMock.getComics(any()) } returns Single.just(data)
        every { comicsMapperMock.map(any()) } returns mockk(relaxed = true)

        repository.getComics(1)
            .test()
            .assertComplete()
            .assertNoErrors()

        verify {
            serviceMock.getComics(any())
            comicsMapperMock.map(any())
        }
    }

    @Test
    fun `get comics error scenario`() {
        val error = Exception()
        every { serviceMock.getComics(any()) } returns Single.error(error)

        repository.getComics(1)
            .test()
            .assertNoValues()
            .assertError(error)

        verify {
            serviceMock.getComics(any())
        }
    }

    @Test
    fun `get series success scenario`() {
        val data = mockk<SeriesResponse>(relaxed = true) {
            every { data.results } returns listOf(
                mockk(relaxed = true)
            )
        }

        every { serviceMock.getSeries(any()) } returns Single.just(data)
        every { seriesMapperMock.map(any()) } returns mockk(relaxed = true)

        repository.getSeries(1)
            .test()
            .assertComplete()
            .assertNoErrors()

        verify {
            serviceMock.getSeries(any())
            seriesMapperMock.map(any())
        }
    }

    @Test
    fun `get series error scenario`() {
        val error = Exception()
        every { serviceMock.getSeries(any()) } returns Single.error(error)

        repository.getSeries(1)
            .test()
            .assertNoValues()
            .assertError(error)

        verify {
            serviceMock.getSeries(any())
        }
    }

}