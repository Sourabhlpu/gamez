package sourabh.pal.cricket.common.domain.model.auth

data class UnauthenticatedUser(
    private val userName: String,
    private val email: String,
    private val password: String,

) : IAuthUser