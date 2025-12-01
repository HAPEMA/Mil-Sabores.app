package cl.milsabores.app.feature.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update



class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun updateProfile(name: String, email: String, phone: String) {
        _uiState.update {
            it.copy(name = name, email = email, phone = phone)
        }
    }

    fun updateAvatar(uri: String?) {
        _uiState.update { it.copy(avatarUrl = uri) }
    }
}
