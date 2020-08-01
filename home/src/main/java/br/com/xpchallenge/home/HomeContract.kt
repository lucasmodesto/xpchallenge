package br.com.xpchallenge.home

import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.ui.core.BaseView
import br.com.xpchallenge.ui.core.IBasePresenter

object HomeContract {

    interface View: BaseView {
        fun showCharacters(characters: List<Character>)
    }

    interface Presenter: IBasePresenter<View> {
        fun loadCharacters(search: String? = null)
        fun loadFavorites()
        fun updateFavorite(character: Character)
    }
}