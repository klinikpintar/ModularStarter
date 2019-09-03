package id.medigo.local.base

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import id.medigo.local.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

private const val DATABASE = "DATABASE"
@RunWith(AndroidJUnit4::class)
abstract class BaseTest: KoinTest {

    @Rule
    @JvmField val instantExecutorRule = InstantTaskExecutorRule()
    protected val database: AppDatabase by inject(named(DATABASE))

    @Before
    open fun setUp(){
        this.configureDi()
    }

    @After
    open fun tearDown() {
        stopKoin()
    }

    // CONFIGURATION
    private fun configureDi(){
        startKoin{
            modules( configureLocalModuleTest(ApplicationProvider.getApplicationContext<Context>()))
        }
    }

    private fun configureLocalModuleTest(context: Context) = module {
        single(named(DATABASE)) {
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        }
        factory { (get(named(DATABASE)) as AppDatabase).profileDao() }
        factory { (get(named(DATABASE)) as AppDatabase).reposDao() }
        factory { (get(named(DATABASE)) as AppDatabase).preferenceDao() }
    }

}