package br.com.xpchallenge.router

import br.com.xpchallenge.presentation.CharacterViewObject

sealed class RouteData {
    object WithoutData : RouteData()
    data class CharacterDetailData(val character: CharacterViewObject) : RouteData()
}