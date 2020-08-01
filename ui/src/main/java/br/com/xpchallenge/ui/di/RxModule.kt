package br.com.xpchallenge.ui.di

import br.com.xpchallenge.ui.core.rx.ISchedulersProvider
import br.com.xpchallenge.ui.core.rx.SchedulersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RxModule {

    @Binds
    abstract fun bindSchedulerProvider(schedulersProviderImpl: SchedulersProvider): ISchedulersProvider
}