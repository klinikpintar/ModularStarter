package id.medigo.common.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.medigo.common.R
import kotlinx.android.synthetic.main._custom_field.view.*


class CustomField : LinearLayoutCompat {

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        setupProperty(context,  attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle){
        setupProperty(context,  attrs)
    }

    private fun setupProperty(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout._custom_field, this, true)

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomField,
            0, 0)
        settingProperty(context, typedArray)
    }

    private fun settingProperty(context: Context, typedArray: TypedArray){
        val inputType = typedArray.getInt(R.styleable.CustomField_inputType,0)
        val value = typedArray.getString(R.styleable.CustomField_value)
        val label = typedArray.getString(R.styleable.CustomField_label)
        val hint = typedArray.getString(R.styleable.CustomField_hint)
        val helperText = typedArray.getString(R.styleable.CustomField_helperText)
        val errorText = typedArray.getString(R.styleable.CustomField_errorText)
        val textLength = typedArray.getInt(R.styleable.CustomField_textLength, 500)

        ctv_label.text = label
        cet_input.hint = hint
        setValue(value)
        ctv_helper.text = helperText
        ctv_error.text = errorText

        // Handle InputType
        when (inputType) {
            1 -> cet_input.inputType = InputType.TYPE_CLASS_NUMBER
            2 -> cet_input.inputType = InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            3 -> cet_input.inputType = InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD
            4 -> cet_input.inputType = InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            else -> cet_input.inputType = InputType.TYPE_CLASS_TEXT
        }

        cet_input.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setErrorVisibility(true)
            }

        })

        typedArray.recycle()
    }

    fun setValue(data: String?){
        cet_input.setText(data?: "")
    }

    fun getValue(): String = cet_input.text?.toString()?: ""

    fun getField(): AppCompatEditText? = cet_input

    fun errorText(message: String?){
        ctv_error.text = message
    }

    @SuppressLint("RestrictedApi")
    fun isValid(isValid: Boolean?){
        if (isValid == false) {
            cet_input.supportBackgroundTintList = ColorStateList.valueOf(getColor(this.context, R.color.error))
        } else {
            cet_input.supportBackgroundTintList = ColorStateList.valueOf(getColor(this.context, R.color.lightergrey))
            cet_input.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                cet_input.supportBackgroundTintList = ColorStateList.valueOf(
                    if (hasFocus) getColor(v.context, R.color.colorAccent) else getColor(v.context, R.color.lightergrey)
                )
            }
        }
        setErrorVisibility(isValid)
    }

    private fun setErrorVisibility(isValid: Boolean?){
        if (isValid == false) {
            ctv_error.visibility = View.VISIBLE
            aciv_error.visibility = View.VISIBLE
            ctv_helper.visibility = View.GONE
        } else {
            ctv_error.visibility = View.GONE
            aciv_error.visibility = View.GONE
            ctv_helper.visibility = View.VISIBLE
        }
    }
}