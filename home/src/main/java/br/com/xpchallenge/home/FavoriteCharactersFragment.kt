package br.com.xpchallenge.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import br.com.xpchallenge.di.CharacterDetailRoute
import br.com.xpchallenge.presentation.model.CharacterViewObject
import br.com.xpchallenge.router.IRoute
import br.com.xpchallenge.presentation.core.BaseFragment
import br.com.xpchallenge.presentation.extensions.setIsVisible
import br.com.xpchallenge.presentation.recyclerview.GridItemDecoration
import br.com.xpchallenge.router.CharacterDetailRouteData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_characters.*
import kotlinx.android.synthetic.main.fragment_search_characters.*
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteCharactersFragment : BaseFragment(), HomeContract.FavoritesView {

    @Inject
    lateinit var presenter: HomeContract.FavoritesPresenter

    @Inject
    @CharacterDetailRoute
    lateinit var characterDetailRoute: IRoute<CharacterDetailRouteData>

    private val _adapter by lazy { CharacterAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        presenter.attach(this)
        presenter.subscribeToFavorites()
    }

    override fun onDestroyView() {
        presenter.detach()
        super.onDestroyView()
    }

    private fun setupRecyclerView() {
        favorite_characters_recycler_view?.run {
            layoutManager =
                GridLayoutManager(context, resources.getInteger(R.integer.grid_span_count))
            adapter = _adapter
            addItemDecoration(GridItemDecoration())
        }

        _adapter.onFavoriteItemClick = {
            presenter.toggleFavorite(it)
        }

        _adapter.onItemClick = {
            characterDetailRoute.open(
                context = context,
                parameters = CharacterDetailRouteData(character = it)
            )
        }
    }

    override fun updateFavorites(characters: List<CharacterViewObject>) {
        _adapter.clear()
        _adapter.update(characters)
    }

    override fun showEmptyFavorites() {
        favorite_characters_empty_state_view?.setIsVisible(true)
    }

    override fun hideEmptyFavorites() {
        favorite_characters_empty_state_view?.setIsVisible(false)
    }

    override fun showError(message: Int, retryAction: () -> Unit) {
        context?.let {
            Snackbar.make(
                search_fragment_root_viewgroup,
                message,
                Snackbar.LENGTH_INDEFINITE
            ).setActionTextColor(ContextCompat.getColor(it, android.R.color.white))
                .setAction(getString(R.string.message_retry)) {
                    retryAction.invoke()
                }.show()
        }
    }
}