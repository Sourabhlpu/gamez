package sourabh.pal.cricket.common.data

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import org.threeten.bp.LocalDateTime
import sourabh.pal.cricket.common.domain.NetworkException
import sourabh.pal.cricket.common.domain.model.player.Player
import sourabh.pal.cricket.common.domain.model.player.details.Contact
import sourabh.pal.cricket.common.domain.model.player.details.Location
import sourabh.pal.cricket.common.domain.model.player.details.PlayerDetails
import sourabh.pal.cricket.common.domain.model.player.details.PlayerWithDetails
import sourabh.pal.cricket.common.domain.model.sport.Sport
import sourabh.pal.cricket.common.domain.model.sport.SportType
import sourabh.pal.cricket.common.domain.pagination.PaginatedPlayers
import sourabh.pal.cricket.common.domain.pagination.Pagination
import sourabh.pal.cricket.common.domain.repositories.PlayerRepository
import javax.inject.Inject

class FakeRepository @Inject constructor() : PlayerRepository {

    private val localSport = Sport(
        id = 43,
        name = "Golf",
        sportType = SportType.OUTDOOR,
        1
    )


    private val remoteSport = Sport(
        id = 3,
        name = "Football",
        sportType = SportType.OUTDOOR,
        11
    )

    private val localContact = Contact(
        email = "frodofromshire@gmail.com",
        phone = "8401231243"
    )

    private val remoteContact = Contact(
        email = "gandalf@gmail.com",
        phone = "3524235435"
    )

    private val localLocation = Location(
        lat = 13.4,
        longitude = -23.4
    )

    private val remoteLocation = Location(
        lat = -52.3,
        longitude = 23.5
    )

    private val interestedSports = listOf(localSport)
    private val remoteInterestedSports = listOf(remoteSport)

    private val localPlayerDetails = PlayerDetails(
        contact = localContact,
        location = localLocation,
        interestedSports = interestedSports
    )

    private val localPlayer = PlayerWithDetails(
        id = 10,
        username = "frodo",
        createdAt = LocalDateTime.now(),
        details = localPlayerDetails
    )

    private val remotePlayerDetails = PlayerDetails(
        contact = remoteContact,
        location = remoteLocation,
        interestedSports = remoteInterestedSports
    )

    private val remotePlayer = PlayerWithDetails(
        id = 15,
        username = "Gandalf",
        createdAt = LocalDateTime.now(),
        details = remotePlayerDetails
    )

    val localPlayers: List<Player> get() = mutableLocalPlayers.map { it.toPlayer() }
    private val mutableLocalPlayers = mutableListOf(localPlayer)

    val remotePlayers: List<Player> get() = mutableRemotePlayers.map { it.toPlayer() }
    private val mutableRemotePlayers = mutableListOf(remotePlayer)

    var failApi = false

    override fun getNearbyPlayers(): Flowable<List<Player>> {
        if (failApi) {
            failApi = false
            throw NetworkException("Cannot fetch players due to network exception")
        } else return Observable.just(localPlayers)
            .toFlowable(BackpressureStrategy.LATEST)
    }

    override suspend fun requestMorePlayers(pageToLoad: Int, numberOfItems: Int): PaginatedPlayers {
         return if (failApi) {
            failApi = false
            throw NetworkException("Cannot fetch players due to network exception")
        } else PaginatedPlayers(
            mutableRemotePlayers,
            Pagination(currentPage = 2, totalPages = 2)
        )
    }

    override suspend fun storePlayers(players: List<PlayerWithDetails>) {
        mutableLocalPlayers.addAll(players)
    }

    private fun PlayerWithDetails.toPlayer(): Player {
        return Player(id, username, createdAt)
    }

}