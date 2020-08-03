package br.com.xpchallenge.presentation.mapper

import br.com.xpchallenge.presentation.model.CharacterViewObject

interface ICharacterViewObjectMapper<T> {

    fun map(characterEntity: T): CharacterViewObject
}