package br.com.xpchallenge.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.xpchallenge.di.CharacterDetailRoute
import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.router.IRoute
import br.com.xpchallenge.router.RouteData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: HomePresenter

    @Inject
    @CharacterDetailRoute
    lateinit var route: IRoute<RouteData.CharacterDetailData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        presenter.fetchCharacters()

        btn?.setOnClickListener {
            route.open(this, RouteData.CharacterDetailData(character = Character(
                name = "",
                imageUrl = ""
            )))
        }
    }
}