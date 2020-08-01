package br.com.xpchallenge.home.search

import br.com.xpchallenge.home.CharacterViewObject
import br.com.xpchallenge.ui.core.BaseView
import br.com.xpchallenge.ui.core.IBasePresenter

object SearchCharactersContract {

    interface View : BaseView {
        fun showCharacters(characters: List<CharacterViewObject>)
    }

    interface Presenter : IBasePresenter<View> {
        fun loadCharacters(search: String? = null)
    }
}