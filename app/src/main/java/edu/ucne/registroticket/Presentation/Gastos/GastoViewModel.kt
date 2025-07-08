package edu.ucne.registroticket.Presentation.Gastos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroticket.Data.Remote.Resource
import edu.ucne.registroticket.Data.Remote.dto.GastoDto
import edu.ucne.registroticket.Data.Remote.repository.GastoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GastoViewModel @Inject constructor(
    private val gastoRepository: GastoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GastoUiState())
    val state = _state.asStateFlow()

    private fun getGastos() {
        viewModelScope.launch {
            gastoRepository.getGastos().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(gastos = result.data ?: emptyList(), isLoading = false)
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(errorMessage = "Ha ocurrido un error, intente de nuevo..", isLoading = false)
                        }
                    }
                }
            }
        }
    }

    fun postGastos() {
        val currentGasto = _state.value.gasto

        if (currentGasto.descripcion.isNullOrBlank()) {
            _state.update {
                it.copy(errorMessage = "La descripción es requerida")
            }
            return
        }

        if (currentGasto.monto == null || currentGasto.monto <= 0) {
            _state.update {
                it.copy(errorMessage = "El monto debe ser mayor a 0")
            }
            return
        }

        viewModelScope.launch {
            try {
                gastoRepository.postGastos(currentGasto).collectLatest { result ->
                    when(result){
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoading = true) }
                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    successMessage = "Guardado correctamente",
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    errorMessage = result.message ?: "Ocurrio un error, intentelo de nuevo",
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Error de conexión: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun deleteGasto(){
        viewModelScope.launch {
            gastoRepository.deleteGasto(_state.value.gasto.gastoId).collectLatest { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                successMessage = "Eliminado correctamente",
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                errorMessage = "Ocurrio un error, intentelo de nuevo",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun getGastoById(gastoId: Int) {
        viewModelScope.launch {
            gastoRepository.getGastoById(gastoId).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                gasto = result.data ?: GastoDto(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                errorMessage = "Ocurrio un error, intentelo de nuevo",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


    fun onEvent(event: GastoEvent) {
        when (event) {
            is GastoEvent.GastoChange -> {
                _state.update {
                    it.copy(
                        gasto = it.gasto.copy(gastoId = event.gastoId)
                    )
                }
            }


            is GastoEvent.DescripcionChange -> {
                _state.update {
                    it.copy(
                        gasto = it.gasto.copy(descripcion = event.descripcion)
                    )
                }
            }

            is GastoEvent.MontoChange -> {
                _state.update {
                    it.copy(
                        gasto = it.gasto.copy(monto = event.monto)
                    )
                }
            }

            GastoEvent.Save -> {
                postGastos()
            }

            GastoEvent.New -> {
                _state.update {
                    it.copy(
                        gasto = GastoDto(),
                        successMessage = null,
                        errorMessage = "",
                        isLoading = false
                    )
                }
            }

            GastoEvent.Delete -> {
                deleteGasto()
            }

            GastoEvent.FindById -> {
                getGastoById(_state.value.gasto.gastoId ?: 0)
            }

            GastoEvent.LoadGastos -> {
                getGastos()
            }
        }
    }
}