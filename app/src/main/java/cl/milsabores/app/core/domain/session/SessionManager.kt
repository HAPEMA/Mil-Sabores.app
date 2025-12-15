package cl.milsabores.app.core.domain.session

import cl.milsabores.app.core.data.local.user.UserEntity


object SessionManager {
    var currentUser: UserEntity? = null

    val isAdmin: Boolean
        get() = currentUser?.rol == "admin"
}
