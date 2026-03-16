package com.meditation.tally.di

import android.content.Context
import androidx.room.Room
import com.meditation.tally.data.local.database.AppDatabase
import com.meditation.tally.data.preferences.DataStorePreferencesRepository
import com.meditation.tally.data.preferences.PreferencesRepository
import com.meditation.tally.data.repository.DailyTallyRepository
import com.meditation.tally.data.repository.DailyTallyRepositoryImpl
import com.meditation.tally.data.repository.UndoSessionManager
import com.meditation.tally.util.DateProvider
import com.meditation.tally.util.DefaultDateProvider
import java.time.Clock

class AppContainer(context: Context) {
    private val appContext = context.applicationContext
    private val clock: Clock = Clock.systemDefaultZone()
    val dateProvider: DateProvider = DefaultDateProvider(clock)

    private val database: AppDatabase = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java,
        "meditation_tally.db"
    ).build()

    val preferencesRepository: PreferencesRepository = DataStorePreferencesRepository(appContext)
    val undoSessionManager: UndoSessionManager = UndoSessionManager()

    val dailyTallyRepository: DailyTallyRepository = DailyTallyRepositoryImpl(
        database = database,
        dao = database.dailyRecordDao(),
        dateProvider = dateProvider
    )
}

