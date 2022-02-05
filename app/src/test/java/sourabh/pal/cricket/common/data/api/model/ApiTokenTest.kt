package sourabh.pal.cricket.common.data.api.model

import com.github.javafaker.Faker
import org.junit.Assert.*
import org.junit.Test

class ApiTokenTest{
    val faker = Faker()

    @Test
    fun givenValidToken_expiryTimeShouldBeGreaterThanZero(){
        //GIVEN
        val expiresInSeconds = 120
        val validToken = ApiToken(
            faker.random().toString(),
            120,
            faker.random().toString()
        )

        val expiresIn = validToken.expiresAt

        //THEN
        assert(expiresIn > 0)
        assert(expiresIn > expiresInSeconds)
    }

    @Test
    fun givenInvalidToken_expiryTimeShouldBeZero(){
        //GIVEN
        val expiredToken = ApiToken(
            faker.random().toString(),
            null,
            faker.random().toString()
        )

        //WHEN
        val expiresIn = expiredToken.expiresAt

        //THEN
        assert(expiresIn <= 0L)
    }

    @Test
    fun givenInvalidToken_withNullTokenType_returnsFalse(){
        //GIVEN
        val expiredToken = ApiToken(
            null,
            null,
            faker.random().toString()
        )

        //WHEN
        val isValidToken = expiredToken.isValid()

        //THEN
        assertFalse(isValidToken)
    }

    @Test
    fun givenValidToken_withNonNullTokenName_returnsTrue(){
        val expiredToken = ApiToken(
            faker.random().toString(),
            2,
            faker.random().toString()
        )

        //WHEN
        val isValidToken = expiredToken.isValid()

        //THEN
        assertTrue(isValidToken)
    }

    @Test
    fun givenValidToken_withValidExpiryTime_returnsTrue(){
        val expiredToken = ApiToken(
            faker.random().toString(),
            2,
            faker.random().toString()
        )

        //WHEN
        val isValidToken = expiredToken.isValid()

        //THEN
        assertTrue(isValidToken)
    }

    @Test
    fun givenInValidToken_withInValidExpiryTime_returnsTrue(){
        val expiredToken = ApiToken(
            faker.random().toString(),
            0,
            faker.random().toString()
        )

        //WHEN
        val isValidToken = expiredToken.isValid()

        //THEN
        assertFalse(isValidToken)
    }

    @Test
    fun givenValidToken_withValidToken_returnsTrue(){
        val expiredToken = ApiToken(
            faker.random().toString(),
            2,
            faker.random().toString()
        )

        //WHEN
        val isValidToken = expiredToken.isValid()

        //THEN
        assertTrue(isValidToken)
    }

    @Test
    fun givenInValidToken_withInValidToken_returnsFalse(){
        val expiredToken = ApiToken(
            faker.random().toString(),
            2,
            ""
        )

        //WHEN
        val isValidToken = expiredToken.isValid()

        //THEN
        assertFalse(isValidToken)
    }

}
