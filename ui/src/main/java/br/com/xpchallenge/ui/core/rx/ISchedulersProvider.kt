package br.com.xpchallenge.ui.core.rx

import io.reactivex.rxjava3.core.Scheduler

interface ISchedulersProvider {
    fun io(): Scheduler
    fun main(): Scheduler
}