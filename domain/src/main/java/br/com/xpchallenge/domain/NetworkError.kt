package br.com.xpchallenge.domain

sealed class NetworkError : Throwable() {
    class NotConnected : NetworkError()
    class SlowConnection : NetworkError()
    class Canceled : NetworkError()
}