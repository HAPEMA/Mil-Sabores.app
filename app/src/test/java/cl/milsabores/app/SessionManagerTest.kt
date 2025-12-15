package cl.milsabores.app

import cl.milsabores.app.core.domain.session.SessionManager
import cl.milsabores.app.core.data.local.user.UserEntity
import org.junit.Assert.*
import org.junit.Test

class SessionManagerTest {

    @Test
    fun login_y_logout_funcionan_correctamente() {
        val user = UserEntity(
            id = 1,
            nombres = "Admin",
            apellidoP = "Test",
            apellidoM = "Test",
            email = "admin@test.cl",
            password = "1234",
            birthDate = "01-01-2000",
            rol = "admin"
        )

        SessionManager.currentUser = user

        assertNotNull(SessionManager.currentUser)
        assertTrue(SessionManager.isAdmin)

        SessionManager.logout()

        assertNull(SessionManager.currentUser)
        assertFalse(SessionManager.isAdmin)
    }
}
