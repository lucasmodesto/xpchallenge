package br.com.xpchallenge.data.mapper

import br.com.xpchallenge.data.remote.model.ComicsResponse
import br.com.xpchallenge.domain.entity.Comic
import javax.inject.Inject

class ComicEntityMapper @Inject constructor() :
    IComicEntityMapper<ComicsResponse.Data.ComicResponse> {

    override fun map(response: ComicsResponse.Data.ComicResponse): Comic {
        return Comic(
            id = response.id,
            name = response.title,
            imageUrl = formatImageUrl(response.thumbnail)
        )
    }

    private fun formatImageUrl(thumbnail: ComicsResponse.Data.ComicResponse.Thumbnail): String {
        return thumbnail.path.replace("http://", "https://") + "." + thumbnail.extension
    }


}