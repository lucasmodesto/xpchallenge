package br.com.xpchallenge.router

import br.com.xpchallenge.domain.entity.Character

sealed class RouteData {
    object WithoutData : RouteData()
    class CharacterDetailData(character: Character) : RouteData()
}