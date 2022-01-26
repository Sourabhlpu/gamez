package sourabh.pal.cricket.common.domain.model.sport

data class Sport(
    val id: Long,
    val name: String,
    val sportType: SportType,
    val playersInTeam: Int
){
    val isTeamSport
    get() = playersInTeam > 1

    val isIndividualSport
    get() = playersInTeam == 1
}