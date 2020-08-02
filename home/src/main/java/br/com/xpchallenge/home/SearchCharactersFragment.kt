package br.com.xpchallenge.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.xpchallenge.di.CharacterDetailRoute
import br.com.xpchallenge.presentation.CharacterViewObject
import br.com.xpchallenge.router.IRoute
import br.com.xpchallenge.router.RouteData
import br.com.xpchallenge.ui.core.BaseFragment
import br.com.xpchallenge.ui.recyclerview.GridItemDecoration
import com.jakewharton.rxbinding4.appcompat.queryTextChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_search_characters.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SearchCharactersFragment : BaseFragment(), HomeContract.View {

    @Inject
    lateinit var presenter: HomeContract.Presenter

    @Inject
    @CharacterDetailRoute
    lateinit var characterDetailRoute: IRoute<RouteData.CharacterDetailData>

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
                    parameters = RouteData.CharacterDetailData(character = it)
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
            .subscribe { text ->
                _adapter.clear()
                presenter.resetPage()
                presenter.loadCharacters(search = text.toString().takeIf { it.isNotEmpty() })
            }
    }

    private fun setupSwipeRefreshView() {
        swipe_refresh_layout.setOnRefreshListener {
            _adapter.clear()
            presenter.resetPage()
            presenter.loadCharacters(
                search = searchview.query.toString().takeIf { it.isNotEmpty() })
        }
    }

    override fun showLoading() {
        swipe_refresh_layout.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_refresh_layout.isRefreshing = false
    }

    override fun showCharacters(characters: List<CharacterViewObject>) {
        _adapter.update(characters)
    }

    override fun clearSearch() {
        // TODO
    }

    override fun updateFavorites(characters: List<CharacterViewObject>) {
        _adapter.updateFavorites(characters)
    }
}