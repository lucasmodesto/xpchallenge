package br.com.xpchallenge.router

import android.content.Context

interface IRoute<T> {
    fun open(context: Context?, parameters: T)
}