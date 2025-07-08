package edu.ucne.registroticket.Presentation.Gastos

sealed interface GastoEvent {
    data class GastoChange(val gastoId: Int): GastoEvent
    data class DescripcionChange(val descripcion: String): GastoEvent
    data class MontoChange(val monto: Double): GastoEvent
    data object Save: GastoEvent
    data object Delete: GastoEvent
    data object New: GastoEvent
    data object FindById: GastoEvent
    data object LoadGastos : GastoEvent
}