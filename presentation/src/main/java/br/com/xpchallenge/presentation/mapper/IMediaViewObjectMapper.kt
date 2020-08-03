package br.com.xpchallenge.presentation.mapper

import br.com.xpchallenge.presentation.model.MediaViewObject

interface IMediaViewObjectMapper<T> {

    fun map(entity: T): MediaViewObject
}