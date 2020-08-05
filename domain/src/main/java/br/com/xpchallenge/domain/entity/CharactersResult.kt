package br.com.xpchallenge.domain.entity

data class CharactersResult(
    val characters: List<Character>,
    val totalCount: Int,
    val count: Int,
    val offset: Int
)
