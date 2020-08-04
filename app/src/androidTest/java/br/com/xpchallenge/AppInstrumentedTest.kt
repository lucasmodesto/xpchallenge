package br.com.xpchallenge

import androidx.test.rule.ActivityTestRule
import br.com.xpchallenge.home.HomeActivity
import br.com.xpchallenge.home.HomeContract
import br.com.xpchallenge.home.di.PresenterModule
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.MockKAnnotations
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(PresenterModule::class)
@HiltAndroidTest
class AppInstrumentedTest {

    @Rule
    @JvmField
    var hiltRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<HomeActivity> =
        ActivityTestRule(
            HomeActivity::class.java,
            true,
            true
        )

    @BindValue
    @JvmField
    val presenterMock: HomeContract.Presenter = mockk(relaxed = true)

    @Before
    fun before() {
        hiltRule.inject()
        MockKAnnotations.init(this)
    }

    @Test
    fun something() {
        Thread.sleep(5000L)
    }

}