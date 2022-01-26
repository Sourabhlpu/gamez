package sourabh.pal.cricket.common.domain.model.sport

import org.junit.Assert.*
import org.junit.Test

class SportTest{

    @Test
    fun isTeamSport_whenPlayerInTeamIsMoreThanOne(){
        //Given
        val sport = Sport(1, "Cricket", SportType.OUTDOOR, 11)

        //Then
        assertTrue(sport.isTeamSport)
        assertFalse(sport.isIndividualSport)
    }

    @Test
    fun isIndividualSport_whenPlayerInTeamIsOne(){
        //Given
        val sport = Sport(2, "Tennis", SportType.OUTDOOR, 1)

        //Then
        assertTrue(sport.isIndividualSport)
        assertFalse(sport.isTeamSport)
    }

}