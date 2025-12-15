package cl.milsabores.app

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import cl.milsabores.app.core.data.local.AppDatabase
import cl.milsabores.app.core.data.local.user.UserDao
import cl.milsabores.app.core.data.local.user.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = db.userDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertar_y_buscar_usuario_por_email() = runBlocking {
        val user = UserEntity(
            nombres = "Juan",
            apellidoP = "Perez",
            apellidoM = "Test",
            email = "juan@test.cl",
            password = "1234",
            birthDate = "01-01-2000"
        )

        userDao.insert(user)

        val result = userDao.findByEmail("juan@test.cl")

        assertEquals("Juan", result?.nombres)
    }
}
