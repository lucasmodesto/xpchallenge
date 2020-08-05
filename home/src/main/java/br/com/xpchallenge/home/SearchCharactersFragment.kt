package br.com.xpchallenge.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.xpchallenge.di.CharacterDetailRoute
import br.com.xpchallenge.presentation.model.CharacterViewObject
import br.com.xpchallenge.router.IRoute
import br.com.xpchallenge.presentation.core.BaseFragment
import br.com.xpchallenge.presentation.extensions.setIsVisible
import br.com.xpchallenge.presentation.recyclerview.GridItemDecoration
import br.com.xpchallenge.router.CharacterDetailRouteData
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.appcompat.queryTextChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_search_characters.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SearchCharactersFragment : BaseFragment(), HomeContract.SearchCharactersView {

    @Inject
    lateinit var presenter: HomeContract.SearchCharactersPresenter

    @Inject
    @CharacterDetailRoute
    lateinit var characterDetailRoute: IRoute<CharacterDetailRouteData>

    private val _adapter by lazy { CharacterAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        setupSwipeRefreshView()
        presenter.attach(this)
        presenter.resetPage()
        presenter.loadCharacters()
        presenter.subscribeToFavorites()
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    private fun setupRecyclerView() {
        _adapter.run {
            onFavoriteItemClick = {
                presenter.toggleFavorite(it)
            }

            onItemClick = {
                characterDetailRoute.open(
                    context = context,
                    parameters = CharacterDetailRouteData(character = it)
                )
            }
        }

        search_characters_recycler_view?.run {
            layoutManager =
                GridLayoutManager(context, resources.getInteger(R.integer.grid_span_count))
            adapter = _adapter
            addItemDecoration(GridItemDecoration())
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1) &&
                        newState == RecyclerView.SCROLL_STATE_IDLE
                    ) {
                        presenter.loadCharacters()
                    }
                }
            })
        }
    }

    private fun setupSearchView() {
        searchview.queryTextChanges()
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .skip(1)
            .subscribe { text ->
                _adapter.clear()
                presenter.resetPage()
                presenter.loadCharacters(query = text.toString().takeIf { it.isNotEmpty() })
            }
    }

    private fun setupSwipeRefreshView() {
        swipe_refresh_layout?.setOnRefreshListener {
            _adapter.clear()
            presenter.resetPage()
            presenter.loadCharacters(
                query = searchview.query.toString().takeIf { it.isNotEmpty() }
            )
        }
    }

    override fun showLoading() {
        swipe_refresh_layout?.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_refresh_layout?.isRefreshing = false
    }

    override fun showCharacters(characters: List<CharacterViewObject>) {
        _adapter.update(characters)
    }

    override fun updateFavorites(characters: List<CharacterViewObject>) {
        _adapter.updateFavorites(characters)
    }

    override fun showEmptyFavorites() {
        // Not applied to this view as it just need to update favorites
    }

    override fun hideEmptyFavorites() {
        // Not applied to this view as it just need to update favorites
    }

    override fun showEmptyState() {
        empty_state_textview?.setIsVisible(true)
    }

    override fun hideEmptyState() {
        empty_state_textview?.setIsVisible(false)
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
