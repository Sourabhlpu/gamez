package sourabh.pal.cricket.common.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
import sourabh.pal.cricket.common.data.api.utils.FakeServer
import sourabh.pal.cricket.common.data.cache.Cache
import sourabh.pal.cricket.common.data.cache.GameDatabase
import sourabh.pal.cricket.common.data.cache.RoomCache
import sourabh.pal.cricket.common.data.di.CacheModule
import sourabh.pal.cricket.common.data.di.PreferencesModule
import sourabh.pal.cricket.common.data.preferences.FakePreferences
import sourabh.pal.cricket.common.data.preferences.Preferences
import sourabh.pal.cricket.common.domain.repositories.PlayerRepository
import java.time.Instant
import javax.inject.Inject
import kotlin.math.exp

@HiltAndroidTest
@UninstallModules(PreferencesModule::class, CacheModule::class)
class PlayerRepositoryTest {

    private val fakeServer = FakeServer()
    private lateinit var repository: PlayerRepository
    private lateinit var api: GameApi
    private lateinit var cache: Cache

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: GameDatabase

    @Inject
    lateinit var retrofitBuilder: Retrofit.Builder

    @BindValue
    @JvmField
    val preferences: Preferences = FakePreferences()

    @Module
    @InstallIn(SingletonComponent::class)
    object TestCacheModule {

        @Provides
        fun provideRoomDatabase(): GameDatabase {
            return Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                GameDatabase::class.java
            )
                .allowMainThreadQueries()
                .build()
        }
    }

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

        cache = RoomCache(database.sportDao(), database.playerDao())

        repository = PlayerRepositoryIml(
            api,
            cache
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