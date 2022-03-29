package sourabh.pal.cricket.common.data.cache.daos

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import sourabh.pal.cricket.common.data.cache.GameDatabase
import sourabh.pal.cricket.common.data.cache.model.cachedplayer.CachedPlayerAggregate

@RunWith(AndroidJUnit4::class)
class PlayerDaoTest{

    private lateinit var gameDatabase: GameDatabase
    private lateinit var playerDao: PlayerDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        gameDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            GameDatabase::class.java).build()
        playerDao = gameDatabase.playerDao()
    }

    @After
    fun closeDb() {
        gameDatabase.close()
    }

    @Test
    fun getAllReturnsEmptyList(){
        verify(playerDao.getAllPlayers().isEmpty)
    }

}