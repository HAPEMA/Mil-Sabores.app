package cl.milsabores.app.core.domain.session

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cl.milsabores.app.core.data.local.user.UserEntity

object SessionManager {
    var currentUser by mutableStateOf<UserEntity?>(null)

    val isLoggedIn: Boolean get() = currentUser != null
    val isAdmin: Boolean get() = currentUser?.rol == "admin"

    fun setUser(user: UserEntity) { currentUser = user }
    fun logout() { currentUser = null }
}
