package id.medigo.common.utils

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import id.medigo.common.ui.CustomField

object BindingUtils {

    @BindingAdapter("app:customFieldValueAttrChanged")
    @JvmStatic
    fun setListener(customField: CustomField, listener: InverseBindingListener) {
        customField.getField()?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = listener.onChange()
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    @BindingAdapter("app:customFieldValue")
    @JvmStatic
    fun setTextValue(customField: CustomField, value: String?) {
        if (value != customField.getValue()) customField.setValue(value)
    }

    @InverseBindingAdapter(attribute = "app:customFieldValue")
    @JvmStatic
    fun getTextValue(customField: CustomField): String? {
        return customField.getValue()
    }

}