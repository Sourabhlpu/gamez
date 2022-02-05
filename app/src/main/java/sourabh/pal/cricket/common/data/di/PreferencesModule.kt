package sourabh.pal.cricket.common.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sourabh.pal.cricket.common.data.preferences.GamePreferences
import sourabh.pal.cricket.common.data.preferences.Preferences

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun providePreferences(preferences: GamePreferences): Preferences
}