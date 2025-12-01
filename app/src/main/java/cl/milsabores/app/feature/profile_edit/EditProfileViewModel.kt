package cl.milsabores.app.feature.profile_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState

    init {
        loadUserData()
    }

    private fun loadUserData() {
        // TODO: Esto debería venir desde repository o Firebase / backend
        _uiState.value = EditProfileUiState(
            name = "Juan Pérez",
            email = "juan@example.com",
            phone = "+56 9 5555 5555"
        )
    }

    fun onNameChanged(newValue: String) {
        _uiState.value = _uiState.value.copy(name = newValue)
    }

    fun onEmailChanged(newValue: String) {
        _uiState.value = _uiState.value.copy(email = newValue)
    }

    fun onPhoneChanged(newValue: String) {
        _uiState.value = _uiState.value.copy(phone = newValue)
    }

    fun saveProfile(onSaved: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(saving = true)

            // Simula un delay de guardado
            kotlinx.coroutines.delay(1200)

            // TODO: Guardar realmente en backend
            _uiState.value = _uiState.value.copy(saving = false)

            onSaved()
        }
    }
}
