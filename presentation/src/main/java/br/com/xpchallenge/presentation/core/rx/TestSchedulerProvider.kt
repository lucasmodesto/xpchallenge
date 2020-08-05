package br.com.xpchallenge.presentation.core.rx

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers.trampoline
import javax.inject.Inject

class TestSchedulerProvider @Inject constructor() : ISchedulersProvider {
    override fun io(): Scheduler = trampoline()
    override fun main(): Scheduler = trampoline()
}
