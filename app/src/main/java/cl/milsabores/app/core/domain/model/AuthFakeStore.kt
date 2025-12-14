package cl.milsabores.app.core.domain.model

object AuthFakeStore {
    // “BD” en memoria (solo para que la app arranque)
    private val users = mutableMapOf<String, String>() // email -> password

    fun register(email: String, password: String): Boolean {
        val em = email.trim().lowercase()
        if (em.isBlank() || password.isBlank()) return false
        if (users.containsKey(em)) return false
        users[em] = password
        return true
    }

    fun login(email: String, password: String): Boolean {
        val em = email.trim().lowercase()
        return users[em] == password
    }
}
