package br.com.xpchallenge.data.mapper

import br.com.xpchallenge.data.remote.model.SeriesResponse
import br.com.xpchallenge.domain.entity.Series
import javax.inject.Inject

class SeriesEntityMapper @Inject constructor() : ISeriesEntityMapper<SeriesResponse.Data.Result> {

    override fun map(response: SeriesResponse.Data.Result): Series {
        return Series(
            id = response.id,
            name = response.title,
            imageUrl = formatImageUrl(response.thumbnail)
        )
    }

    private fun formatImageUrl(thumbnail: SeriesResponse.Data.Result.Thumbnail): String {
        return thumbnail.path.replace(
            "http://",
            "https://"
        ) + "." + thumbnail.extension
    }
}
