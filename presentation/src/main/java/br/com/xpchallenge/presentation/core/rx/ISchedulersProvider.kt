package br.com.xpchallenge.presentation.core.rx

import io.reactivex.rxjava3.core.Scheduler

interface ISchedulersProvider {
    fun io(): Scheduler
    fun main(): Scheduler
}