package br.com.xpchallenge.router

import br.com.xpchallenge.domain.entity.Character

sealed class RouteData {
    object WithoutData : RouteData()
    data class CharacterDetailData(val character: Character) : RouteData()
}