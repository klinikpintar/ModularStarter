package id.medigo.modularstarter.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import id.medigo.modularstarter.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val sharedPreferenceModule = module {
    single { provideSettingsPreferences(androidApplication()) }
}

private const val PREFERENCES_FILE_KEY = BuildConfig.APPLICATION_ID

private fun provideSettingsPreferences(app: Application): SharedPreferences =
    app.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
