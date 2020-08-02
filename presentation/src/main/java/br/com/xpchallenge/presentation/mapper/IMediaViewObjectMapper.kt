package br.com.xpchallenge.presentation.mapper

import br.com.xpchallenge.presentation.adapter.MediaViewObject

interface IMediaViewObjectMapper<T> {

    fun map(entity: T): MediaViewObject
}