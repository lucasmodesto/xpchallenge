package br.com.xpchallenge.presentation.mapper

import br.com.xpchallenge.presentation.CharacterViewObject

interface ICharacterViewObjectMapper<T> {

    fun map(characterEntity: T): CharacterViewObject
}