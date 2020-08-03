package br.com.xpchallenge.presentation.mapper

import br.com.xpchallenge.domain.entity.Comic
import br.com.xpchallenge.presentation.model.MediaViewObject
import javax.inject.Inject

class ComicViewObjectMapper @Inject constructor() : IMediaViewObjectMapper<Comic> {

    override fun map(entity: Comic): MediaViewObject {
        return MediaViewObject(
            id = entity.id,
            name = entity.name,
            imageUrl = entity.imageUrl
        )
    }
}