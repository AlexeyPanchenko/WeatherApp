package ru.aol_panchenko.weatherapp.presentation.add_city


import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import ru.aol_panchenko.weatherapp.R

/**
 * Created by alexey on 17.09.17.
 */
class AddCityDialog : DialogFragment(), AddCityMVPView {

    private var _presenter: AddCityPresenter? = null
    private lateinit var _editCity: EditText

    companion object {
        private val TAG = AddCityDialog::class.java.simpleName

        fun show(fragmentManager: FragmentManager) {
            val dialog = AddCityDialog()
            dialog.show(fragmentManager, TAG)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _presenter = AddCityPresenter(this, ViewModelProviders.of(activity).get(AddCityViewModel::class.java))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val customView = LayoutInflater.from(activity).inflate(R.layout.add_city_dialog, null)
        _editCity = customView.findViewById(R.id.etEnterCity)

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.add_city_title))
                .setPositiveButton(getString(R.string.add_city_button_save),{ _, _ ->
                    _presenter?.onSaveClick()
                })
                .setNegativeButton(getString(R.string.add_city_button_cancel), { _, _ ->
                    _presenter?.onCancelClick()
                })
                .setView(customView)
                .create()

        return builder.show()
    }

    override fun closeDialog() {
        dialog.dismiss()
    }

    override fun getCityName() = _editCity.text.toString()

    override fun onDestroy() {
        super.onDestroy()
        _presenter?.let { _presenter = null }
    }

}