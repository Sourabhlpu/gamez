package sourabh.pal.cricket.common.data.cache.model.cachedsport

import androidx.room.Entity
import androidx.room.PrimaryKey
import sourabh.pal.cricket.common.domain.model.sport.Sport
import sourabh.pal.cricket.common.domain.model.sport.SportType

@Entity(tableName = "sports")
data class CachedSport(
    @PrimaryKey(autoGenerate = false)
    val sportId: Long,
    val name: String,
    val playersInTeam: Int,
    val sportType: String

){
    companion object{
        fun fromDomain(domainModel: Sport): CachedSport {
            return CachedSport(
                sportId = domainModel.id,
                name = domainModel.name,
                playersInTeam = domainModel.playersInTeam,
                sportType = domainModel.sportType.toString()
            )
        }
    }


    fun toDomain(): Sport{
        return Sport(
            id = sportId,
            name = name,
            playersInTeam = playersInTeam,
            sportType = SportType.valueOf(sportType)
        )
    }
}