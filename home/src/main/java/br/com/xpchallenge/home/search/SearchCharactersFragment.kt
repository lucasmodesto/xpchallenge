package br.com.xpchallenge.home.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.xpchallenge.home.CharacterViewObject
import br.com.xpchallenge.home.R
import br.com.xpchallenge.ui.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchCharactersFragment : BaseFragment(), SearchCharactersContract.View {

    @Inject
    lateinit var presenter: SearchCharactersContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun showCharacters(characters: List<CharacterViewObject>) {
        // TODO:
    }
}