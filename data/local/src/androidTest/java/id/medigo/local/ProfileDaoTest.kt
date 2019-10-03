package id.medigo.local

import id.medigo.common_test.datasets.Users.FAKE_USER
import id.medigo.local.base.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ProfileDaoTest : BaseTest() {

    @Test
    fun getSavedProfile() = runBlocking {
        database.profileDao().save(FAKE_USER)
        val profileResult = database.profileDao().getUser()

        assertEquals(FAKE_USER, profileResult)
    }

    @Test
    fun deleteProfile() = runBlocking {
        database.profileDao().save(FAKE_USER)
        database.profileDao().deleteUser()

        val profileResult = database.profileDao().getUser()

        assertNull(profileResult)
    }
}
