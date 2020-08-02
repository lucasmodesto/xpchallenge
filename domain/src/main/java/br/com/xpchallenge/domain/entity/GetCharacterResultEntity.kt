package br.com.xpchallenge.domain.entity

data class GetCharacterResultEntity(
    val characters: List<Character>,
    val totalCount: Int,
    val count: Int,
    val offset: Int
)