package br.com.xpchallenge.data.mapper

import br.com.xpchallenge.domain.entity.Series

interface ISeriesEntityMapper<T> {

    fun map(response: T): Series
}