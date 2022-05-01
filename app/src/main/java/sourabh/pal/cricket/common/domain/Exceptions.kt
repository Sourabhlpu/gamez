package sourabh.pal.cricket.common.domain

import java.io.IOException

class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)
class NoMorePlayersException(message: String): Exception(message)
class NetworkException(message: String): Exception(message)