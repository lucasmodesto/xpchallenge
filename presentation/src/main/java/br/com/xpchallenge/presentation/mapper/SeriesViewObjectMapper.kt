package br.com.xpchallenge.presentation.mapper

import br.com.xpchallenge.domain.entity.Series
import br.com.xpchallenge.presentation.model.MediaViewObject
import javax.inject.Inject

class SeriesViewObjectMapper @Inject constructor() : IMediaViewObjectMapper<Series> {

    override fun map(entity: Series): MediaViewObject {
        return MediaViewObject(
            id = entity.id,
            name = entity.name,
            imageUrl = entity.imageUrl
        )
    }
}
