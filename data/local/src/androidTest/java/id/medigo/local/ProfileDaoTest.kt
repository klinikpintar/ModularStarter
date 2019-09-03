package id.medigo.local

import id.medigo.local.base.BaseTest
import id.medigo.model.Profile
import org.junit.Test

class ProfileDaoTest: BaseTest() {

    private val dummyUser = Profile("22127834","alaskariyy"
        ,"Mahdan Al Askariyy","Medigo Indonesia","Mobile Developer at Medigo Indonesia"
        ,"https://avatars2.githubusercontent.com/u/22127834?v=4")

    @Test
    fun saveAndDeleteProfile(){
        // Save Profile
        database.profileDao().save(dummyUser).test().assertNoErrors()
        // Make sure profile was saved
        database.profileDao().getUser().test().assertValue(dummyUser)
        // Delete profile
        database.preferenceDao().deleteProfile().test().assertNoErrors()
        // Make sure profile was deleted
        database.profileDao().getUser().test().assertValueCount(0)
    }

}