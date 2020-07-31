package br.com.xpchallenge.home

import br.com.xpchallenge.domain.repository.ICharacterRepository
import javax.inject.Inject

class HomePresenter @Inject constructor(val repository: ICharacterRepository) {

    fun fetchCharacters() {
        repository.getCharacters(name = "spider man", paginationOffset = 20)
    }
}