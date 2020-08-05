package br.com.xpchallenge.data.mapper

import br.com.xpchallenge.domain.entity.Comic

interface IComicEntityMapper<T> {

    fun map(response: T): Comic
}
