package id.medigo.modularstarter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * NavController handled by each fragment. You can do any global action here.
 */
class MainActivity : AppCompatActivity() {

    lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreference = getSharedPreferences(getString(R.string.shared_preference_key), Context.MODE_PRIVATE)

    }
}
