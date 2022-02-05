package sourabh.pal.cricket.common.data.api.mappers

interface ApiMapper<E,D> {
    fun mapToDomain(apiEntity: E): D
}