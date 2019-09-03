package id.medigo.local

import id.medigo.local.base.BaseTest
import id.medigo.model.Preference
import org.junit.Test

class PreferensDaoTest: BaseTest() {

    private val dummyUserId = "DUMMY_USER_ID"

    @Test
    fun createAndDeletePreference(){
        // Save preference
        database.preferenceDao().savePreference(Preference(loggedUserId = dummyUserId)).test().assertNoErrors()
        // Make sure the data is saved
        database.preferenceDao().getLoggedInUserId().test().assertValue(dummyUserId)
        // Delete Preference
        database.preferenceDao().deletePreference().test().assertNoErrors()
        // Make sure the Preference was deleted
        database.preferenceDao().getLoggedInUserId().test().assertValueCount(0)
    }
}