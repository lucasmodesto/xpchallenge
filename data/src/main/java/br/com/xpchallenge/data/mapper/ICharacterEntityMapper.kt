package br.com.xpchallenge.data.mapper

import br.com.xpchallenge.domain.entity.Character

interface ICharacterEntityMapper<T> {

    fun map(data: T): Character
}