package br.com.xpchallenge.presentation.core.rx

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class TestSchedulerProvider @Inject constructor() : ISchedulersProvider {

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun main(): Scheduler = Schedulers.trampoline()
}