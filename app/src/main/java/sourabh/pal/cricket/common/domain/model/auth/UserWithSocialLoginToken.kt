package sourabh.pal.cricket.common.domain.model.auth

data class UserWithSocialLoginToken(
    private val unauthenticatedUser: UnauthenticatedUser,
    private val token: Token
): IAuthUser

data class Token(
    val tokenType: String,
    val token: String
)