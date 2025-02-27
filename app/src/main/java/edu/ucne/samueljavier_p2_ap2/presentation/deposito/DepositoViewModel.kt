package edu.ucne.samueljavier_p2_ap2.presentation.deposito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.samueljavier_p2_ap2.data.local.repository.DepositoRepository
import edu.ucne.samueljavier_p2_ap2.data.remote.dto.DepositoDto
import edu.ucne.samueljavier_p2_ap2.data.remote.dto.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DepositoViewModel @Inject constructor(
    private val depositoRepository: DepositoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DepositoUiState())
    val uiState: StateFlow<DepositoUiState> = _uiState.asStateFlow()

    init {
        getDepositos()
    }

    fun saveDeposito() {
        viewModelScope.launch {
            if (isValid()) {
                depositoRepository.saveDeposito(_uiState.value.toEntity())
            }
        }
    }

    fun delete(depositoId: Int) {
        viewModelScope.launch {
            depositoRepository.delete(depositoId)
        }
    }

    fun refreshDepositos() {
        getDepositos()
    }

    fun update() {
        viewModelScope.launch {
            depositoRepository.update(
                DepositoDto(
                    idDeposito = _uiState.value.depositoId,
                    fecha = _uiState.value.fecha,
                    idCuenta = _uiState.value.cuentaId,
                    concepto = _uiState.value.concepto,
                    monto = _uiState.value.monto
                )
            )
        }
    }

    fun new() {
        _uiState.value = DepositoUiState()
    }

    fun onFechaChange(fecha: Date) {
        _uiState.update { it.copy(fecha = fecha) }
    }

    fun onConceptoChange(concepto: String) {
        _uiState.update { it.copy(concepto = concepto) }
    }

    fun onMontoChange(monto: Double) {
        _uiState.update { it.copy(monto = monto) }
    }

    fun onCuentaIdChange(cuentaId: Int) {
        _uiState.update { it.copy(cuentaId = cuentaId) }
    }

    fun find(depositoId: Int) {
        viewModelScope.launch {
            depositoRepository.find(depositoId).let { depositoDto ->
                _uiState.update {
                    it.copy(
                        depositoId = depositoDto.idDeposito,
                        fecha = depositoDto.fecha,
                        concepto = depositoDto.concepto,
                        monto = depositoDto.monto,
                        cuentaId = depositoDto.idCuenta
                    )
                }
            }
        }
    }

    fun getDepositos() {
        viewModelScope.launch {
            depositoRepository.getDepositos().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> _uiState.update {
                        it.copy(depositos = result.data ?: emptyList(), isLoading = false)
                    }
                    is Resource.Error -> _uiState.update {
                        it.copy(errorMessage = result.message ?: "Error desconocido", isLoading = false)
                    }
                    else -> _uiState.update { it.copy(errorMessage = "Estado no manejado") }
                }
            }
        }
    }

    fun isValid(): Boolean {
        return _uiState.value.concepto.isNotBlank() &&
                _uiState.value.monto > 0 &&
                _uiState.value.cuentaId > 0
    }

    private fun DepositoUiState.toEntity() = DepositoDto(
        idDeposito = depositoId,
        fecha = fecha,
        idCuenta = cuentaId,
        concepto = concepto,
        monto = monto
    )
}
