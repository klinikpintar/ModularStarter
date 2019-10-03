package id.medigo.common.extension

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import id.medigo.common.utils.Event

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun Fragment.showSnackbar(snackbarText: String, timeLength: Int) {
    activity?.let { Snackbar.make(it.findViewById<View>(android.R.id.content), snackbarText, timeLength).show() }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun Fragment.setupSnackbar(lifecycleOwner: LifecycleOwner, snackbarEvent: LiveData<Event<String>>, timeLength: Int) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { res ->
            showSnackbar(res, timeLength)
        }
    })
}

fun Fragment.showWarningDialog(title: String = "", message: String = "") {
    activity?.let {
        val dialog = AlertDialog.Builder(it)
        dialog.setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Oke") { _, _ -> }
        if (title.isNotEmpty()) dialog.setTitle(title)
        dialog.show()
    }
}