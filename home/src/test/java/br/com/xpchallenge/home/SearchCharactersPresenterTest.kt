package br.com.xpchallenge.home

import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.domain.repository.ICharacterRepository
import br.com.xpchallenge.presentation.core.rx.TestSchedulerProvider
import br.com.xpchallenge.presentation.mapper.ICharacterViewObjectMapper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import kotlin.Exception

class SearchCharactersPresenterTest {

    private lateinit var presenter: SearchCharactersPresenter

    @MockK
    lateinit var mapperMock: ICharacterViewObjectMapper<Character>

    @MockK
    lateinit var repositoryMock: ICharacterRepository

    @MockK
    lateinit var viewMock: HomeContract.SearchCharactersView

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = SearchCharactersPresenter().apply {
            view = viewMock
            repository = repositoryMock
            characterViewObjectMapper = mapperMock
            schedulersProvider = TestSchedulerProvider()
        }
    }

    @Test
    fun `load characters success scenario`() {
        every {
            repositoryMock.getCharacters(
                any(),
                any()
            )
        } returns Single.just(
            mockk(relaxed = true) {
                every { characters } returns listOf(
                    mockk()
                )
                every { count } returns 20
            }
        )

        every { mapperMock.map(any()) } returns mockk(relaxed = true)

        presenter.loadCharacters()

        verify {
            repositoryMock.getCharacters(any(), any())
            viewMock.showLoading()
            viewMock.hideLoading()
            viewMock.showCharacters(any())
            viewMock.hideEmptyState()
            mapperMock.map(any())
        }

        verify(exactly = 0) {
            viewMock.showError(any(), any())
            viewMock.showEmptyState()
        }

        assert(presenter.paginationOffset == 20)
        assert(!presenter.isLastPage)
    }

    @Test
    fun `load characters error scenario`() {
        every {
            repositoryMock.getCharacters(
                any(),
                any()
            )
        } returns Single.error(Exception())

        presenter.loadCharacters()

        verify {
            repositoryMock.getCharacters(any(), any())
            viewMock.showLoading()
            viewMock.hideLoading()
            viewMock.showError(any(), any())
        }

        verify(exactly = 0) {
            viewMock.showCharacters(any())
            mapperMock.map(any())
        }

        assert(presenter.paginationOffset == 0)
        assert(!presenter.isLastPage)
    }

    @Test
    fun `load characters empty state scenario`() {
        every {
            repositoryMock.getCharacters(
                any(),
                any()
            )
        } returns Single.just(
            mockk(relaxed = true) {
                every { count } returns 20
                every { characters } returns emptyList()
            }
        )

        presenter.loadCharacters()

        verify {
            viewMock.showLoading()
            viewMock.hideLoading()
            viewMock.showEmptyState()
        }
    }

    @Test
    fun `load favorites success scenario`() {
        every { repositoryMock.getFavoriteCharacters() } returns Observable.just(listOf(mockk()))
        every { mapperMock.map(any()) } returns mockk(relaxed = true)

        presenter.subscribeToFavorites()

        verify {
            viewMock.showFavoriteCharacters(any())
            mapperMock.map(any())
        }

        verify(exactly = 0) {
            viewMock.showError(any(), any())
        }
    }

    @Test
    fun `load favorites error scenario`() {
        every { repositoryMock.getFavoriteCharacters() } returns Observable.error(Exception())

        presenter.subscribeToFavorites()

        verify {
            viewMock.showError(any(), any())
        }

        verify(exactly = 0) {
            viewMock.showFavoriteCharacters(any())
        }
    }

    @Test
    fun `last page expected behavior`() {
        presenter.isLastPage = true
        presenter.loadCharacters()

        verify {
            repositoryMock wasNot called
            viewMock wasNot called
        }
    }

    @Test
    fun `reset page expected behavior`() {
        presenter.isLastPage = true
        presenter.paginationOffset = 20

        presenter.resetPage()

        assert(!presenter.isLastPage)
        assert(presenter.paginationOffset == 0)
    }
}
