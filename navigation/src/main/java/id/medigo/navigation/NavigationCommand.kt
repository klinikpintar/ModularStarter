package id.medigo.navigation

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

/**
 * A simple sealed class to handle more properly
 * navigation from a [ViewModel]
 */
sealed class NavigationCommand {
    data class To(val directions: NavDirections): NavigationCommand()
    object Back: NavigationCommand()
    object ClearAll: NavigationCommand()
    data class StartActivityForResult(val activity: Activity,
                                      val requestCode: Int,
                                      val bundle: Bundle
    ) : NavigationCommand()
}