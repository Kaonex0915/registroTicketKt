package edu.ucne.registroticket.Presentation.Gastos

import edu.ucne.registroticket.Data.Remote.dto.GastoDto

data class GastoUiState(
    val gastoId: Int = 0,
    val descripcion: String = "",
    val monto: Double = 0.0,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val gasto: GastoDto = GastoDto(),
    val gastos: List<GastoDto> = emptyList()
)