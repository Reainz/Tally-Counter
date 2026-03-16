package com.meditation.tally

import android.app.Application
import com.meditation.tally.di.AppContainer

class MeditationTallyApplication : Application() {
    val appContainer: AppContainer by lazy { AppContainer(this) }
}

