package dl.useless.ezweather

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.add_city_dialog.view.*

class AddCityDialog : DialogFragment() {

    lateinit var etName: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle(getString(R.string.city_name_title))
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.add_city_dialog, null
        )
        etName = dialogView.et_name
        dialogBuilder.setView(dialogView)
        dialogBuilder.setPositiveButton(getString(R.string.ok)) { dialog, which ->
        }
        dialogBuilder.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
        }
        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etName.text.isNotEmpty()) {
                (context as ActivityCities).getCityName(etName.text.toString())
                dialog!!.dismiss()
            } else {
                etName.error =getString(R.string.empty_field)
            }
        }
    }
}
