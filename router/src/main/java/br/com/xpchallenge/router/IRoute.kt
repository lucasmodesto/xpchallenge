package br.com.xpchallenge.router

import android.content.Context

interface IRoute<T : RouteData> {
    fun open(context: Context?, parameters: T)
}