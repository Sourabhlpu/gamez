package sourabh.pal.cricket.common.data.api.mappers

import sourabh.pal.cricket.common.data.api.model.player.ApiContact
import sourabh.pal.cricket.common.domain.model.player.details.Contact
import javax.inject.Inject

class ApiContactMapper @Inject constructor(): ApiMapper<ApiContact?, Contact> {

    override fun mapToDomain(apiEntity: ApiContact?): Contact {
        if(apiEntity?.email.isNullOrBlank() && apiEntity?.phone.isNullOrBlank())
            throw MappingException("Both Email and Phone Cannot be null or empty")
        return Contact(
            email = apiEntity?.email.orEmpty(),
            phone = apiEntity?.phone.orEmpty()
        )
    }
}