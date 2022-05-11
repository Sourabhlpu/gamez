package sourabh.pal.cricket.common.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import sourabh.pal.cricket.common.data.api.GameApi
import sourabh.pal.cricket.common.data.api.mappers.ApiPaginationMapper
import sourabh.pal.cricket.common.data.api.mappers.ApiPlayerDetailsMapper
import sourabh.pal.cricket.common.data.api.utils.FakeServer
import sourabh.pal.cricket.common.data.cache.Cache
import sourabh.pal.cricket.common.data.cache.GameDatabase
import sourabh.pal.cricket.common.data.cache.RoomCache
import sourabh.pal.cricket.common.data.cache.daos.PlayerDao
import sourabh.pal.cricket.common.data.cache.daos.SportDao
import sourabh.pal.cricket.common.data.di.CacheModule
import sourabh.pal.cricket.common.data.di.PreferencesModule
import sourabh.pal.cricket.common.data.preferences.FakePreferences
import sourabh.pal.cricket.common.data.preferences.Preferences
import sourabh.pal.cricket.common.domain.repositories.PlayerRepository
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.exp

@HiltAndroidTest
@UninstallModules(PreferencesModule::class, CacheModule::class)
class PlayerRepositoryTest {

    private val fakeServer = FakeServer()
    private lateinit var repository: PlayerRepository
    private lateinit var api: GameApi


    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: GameDatabase

    @Inject
    lateinit var cache: Cache

    @Inject
    lateinit var retrofitBuilder: Retrofit.Builder


    @Inject
    lateinit var apiPlayerMapper: ApiPlayerDetailsMapper

    @Inject
    lateinit var apiPaginationMapper: ApiPaginationMapper

    @BindValue
    @JvmField
    val preferences: Preferences = FakePreferences()

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class TestCacheModule{

        @Binds
        abstract fun bindCache(cache: RoomCache): Cache

        companion object {

            @Provides
            fun provideRoomDatabase(): GameDatabase {
                return Room.inMemoryDatabaseBuilder(
                    InstrumentationRegistry.getInstrumentation().context,
                    GameDatabase::class.java
                )
                    .allowMainThreadQueries()
                    .build()
            }

            @Provides
            fun providePlayerDao(gameDatabase: GameDatabase): PlayerDao = gameDatabase.playerDao()

            @Provides
            fun provideSportDao(gameDatabase: GameDatabase): SportDao = gameDatabase.sportDao()
        }
    }
    /*object TestCacheModule {

        @Binds
        fun bindCache(database: GameDatabase): Cache = RoomCache(database.sportDao(), database.playerDao())

        @Provides
        fun provideRoomDatabase(): GameDatabase {
            return Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                GameDatabase::class.java
            )
                .allowMainThreadQueries()
                .build()
        }
    }*/

    @Before
    fun setup(){
        fakeServer.start()

        preferences.deleteTokenInfo()
        preferences.putToken("validToken")
        preferences.putTokenExpirationTime(Instant.now().plusSeconds(3600).epochSecond)
        preferences.putTokenType("Bearer")

        hiltRule.inject()

        api = retrofitBuilder
            .baseUrl(fakeServer.baseEndpoint)
            .build()
            .create(GameApi::class.java)

        /*cache = RoomCache(database.sportDao(), database.playerDao())*/

        repository = PlayerRepositoryIml(
            api,
            cache,
            apiPlayerMapper,
            apiPaginationMapper
        )
    }

    @Test
    fun requestMorePlayers_success() = runBlocking{
        //Given
        val expectedAnimalId =124L
        fakeServer.setHappyPathDispatcher()

        //When
        val paginatedPlayers = repository.requestMorePlayers(1, 100)

        //Then
        val player = paginatedPlayers.players.first()
        assertThat(player.id).isEqualTo(expectedAnimalId)
    }

    @After
    fun tearDown(){
        fakeServer.shutdown()
    }
}