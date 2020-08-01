package br.com.xpchallenge.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.ui.core.BaseFragment
import br.com.xpchallenge.ui.recyclerview.GridItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_characters.*
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteCharactersFragment : BaseFragment(), HomeContract.View {

    @Inject
    lateinit var presenter: HomeContract.Presenter

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
        presenter.loadFavorites()
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
            presenter.updateFavorite(it)
        }

        _adapter.onItemClick = {
            // TODO: detail route
        }
    }

    override fun showCharacters(characters: List<Character>) {
        _adapter.clear()
        _adapter.update(characters)
    }
}