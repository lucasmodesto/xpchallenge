package br.com.xpchallenge.presentation.di

import br.com.xpchallenge.presentation.core.rx.ISchedulersProvider
import br.com.xpchallenge.presentation.core.rx.SchedulersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class ToolsModule {

    @Binds
    abstract fun bindSchedulerProvider(schedulersProviderImpl: SchedulersProvider): ISchedulersProvider
}
