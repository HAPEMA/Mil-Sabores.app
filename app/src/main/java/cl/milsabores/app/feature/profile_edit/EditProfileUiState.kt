package cl.milsabores.app.feature.profile_edit

data class EditProfileUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val saving: Boolean = false
)
