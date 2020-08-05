package br.com.xpchallenge.home

import br.com.xpchallenge.data.di.RepositoryModule
import br.com.xpchallenge.domain.entity.CharactersResult
import br.com.xpchallenge.domain.errors.NetworkError
import br.com.xpchallenge.domain.repository.ICharacterRepository
import br.com.xpchallenge.testcommon.checkIfViewShowsText
import br.com.xpchallenge.testcommon.launchFragmentInHiltContainer
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class SearchCharactersFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @MockK
    lateinit var repository: ICharacterRepository

    @Before
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        hiltRule.inject()
    }

    @Test
    fun shouldShowEmptyStateMessage() {
        every { repository.getCharacters(any(), any()) } returns Single.just(
            CharactersResult(
                characters = emptyList(),
                count = 0,
                totalCount = 0,
                offset = 0))
        every { repository.getFavoriteCharacters() } returns Observable.just(emptyList())

        launchFragmentInHiltContainer<SearchCharactersFragment>()

        checkIfViewShowsText(message = R.string.search_empty_state_message)
    }

    @Test
    fun shouldShowNoInternetMessage() {
        every { repository.getCharacters(any(), any()) } returns Single.error(
            NetworkError.NotConnected()
        )
        every { repository.getFavoriteCharacters() } returns Observable.just(emptyList())

        launchFragmentInHiltContainer<SearchCharactersFragment>()

        checkIfViewShowsText(message = R.string.no_internet_message)
    }

    @Test
    fun shouldShowUnknownErrorMessage() {
        every { repository.getCharacters(any(), any()) } returns Single.error(Exception())
        every { repository.getFavoriteCharacters() } returns Observable.just(emptyList())

        launchFragmentInHiltContainer<SearchCharactersFragment>()

        checkIfViewShowsText(message = R.string.unknown_error_message)
    }

}