package edu.ucne.registroticket.Data.Remote.repository

import edu.ucne.registroticket.Data.Remote.GastoApi
import edu.ucne.registroticket.Data.Remote.Resource
import edu.ucne.registroticket.Data.Remote.dto.GastoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GastoRepository @Inject constructor(
    private val gastoApi: GastoApi
) {
    fun getGastos(): Flow<Resource<List<GastoDto>>> = flow {
        try {
            emit(Resource.Loading())

            val gastos = gastoApi.getGastos()

            emit(Resource.Success(gastos))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error al conectarse con la API"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error inesperado, verificar tu conexion a internet"))
        }
    }

    fun getGastoById(gastoId: Int): Flow<Resource<GastoDto>> = flow {
        try {
            emit(Resource.Loading())

            val gastos = gastoApi.getGastoById(gastoId)

            emit(Resource.Success(gastos))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error al conectarse con la API"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error inesperado, verificar tu conexion a internet"))
        }
    }

    fun postGastos(gastoDto: GastoDto): Flow<Resource<GastoDto>> = flow{
        try {
            emit(Resource.Loading())

            gastoApi.postGastos(gastoDto)
            emit(Resource.Success(gastoDto))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error al conectarse con la API"))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error inesperado, verificar tu conexion a internet"))
        }
    }

    fun deleteGasto(gastoId: Int?): Flow<Resource<GastoDto>> = flow{
        try {
            emit(Resource.Loading())

            gastoApi.deleteGasto(gastoId)
            emit(Resource.Success(GastoDto()))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error al conectarse con la API"))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error inesperado, verificar tu conexion a internet"))
        }
    }
}