package cl.milsabores.app.feature.profile

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val avatarUrl: String? = null,
    val isLoading: Boolean = false
)
