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
import br.com.xpchallenge.domain.entity.Character
import io.mockk.*
import io.mockk.impl.annotations.MockK
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
    fun `load favorites success scenario`() {
        val roomModel = mockk<CharacterDBModel>()
        val character = mockk<Character>()
        val data = listOf(roomModel)
        every { dao.observeCharacters() } returns Observable.just(data)
        every { characterRoomMapper.map(roomModel) } returns character

        repository.getFavoriteCharacters()

        verify {
            dao.observeCharacters()
        }

        repository.getFavoriteCharacters()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue {
                it.size == 1 && it[0] == character
            }
    }

    @Test
    fun `load favorites error scenario`() {
        val error = Exception()
        every { dao.observeCharacters() } returns Observable.error(error)

        repository.getFavoriteCharacters()

        verify {
            dao.observeCharacters()
        }

        repository.getFavoriteCharacters()
            .test()
            .assertNoValues()
            .assertError(error)
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
                        events = mockk(relaxed = true),
                        description = "",
                        modified = "",
                        resourceURI = "",
                        series = mockk(relaxed = true),
                        comics = mockk(relaxed = true),
                        stories = mockk(relaxed = true),
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

        verify {
            service.getCharacters(search)
            dao.getCharacters()
        }

        repository.getCharacters(search)
            .test()
            .assertNoErrors()
            .assertComplete()
    }

}