package sourabh.pal.cricket.common.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sourabh.pal.cricket.common.data.cache.Cache
import sourabh.pal.cricket.common.data.cache.GameDatabase
import sourabh.pal.cricket.common.data.cache.RoomCache
import sourabh.pal.cricket.common.data.cache.daos.PlayerDao
import sourabh.pal.cricket.common.data.cache.daos.SportDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): GameDatabase {
            return Room.databaseBuilder(
                context,
                GameDatabase::class.java,
                "game.db"
            )
                .build()
        }

        @Provides
        fun providePlayerDao(gameDatabase: GameDatabase): PlayerDao = gameDatabase.playerDao()

        @Provides
        fun provideSportDao(gameDatabase: GameDatabase): SportDao = gameDatabase.sportDao()
    }
}