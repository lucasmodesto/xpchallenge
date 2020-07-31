package br.com.xpchallenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class XpChallengeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}