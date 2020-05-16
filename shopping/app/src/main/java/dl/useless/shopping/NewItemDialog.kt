package dl.useless.shopping

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import dl.useless.shopping.data.Item
import kotlinx.android.synthetic.main.item_dialog.view.*

class ItemDialog : DialogFragment() {

    interface ItemHandler {
        fun itemCreated(item:Item)
        fun itemUpdated(item:Item)
    }

    lateinit var itemHandler: ItemHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ItemHandler) {
            itemHandler = context
        } else {
            throw RuntimeException(
                "The Activity is not implementing the ItemHandler interface."
            )
        }
    }

    lateinit var etItemName: EditText
    lateinit var spinnerCategory: Spinner
    lateinit var etItemPrice: EditText
    lateinit var etQuantity: EditText
    lateinit var etDescription: EditText
    lateinit var cbPurchased: CheckBox

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle(getString(R.string.new_item))
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.item_dialog, null
        )

        etItemName = dialogView.et_name
        spinnerCategory = dialogView.et_Category
        etItemPrice = dialogView.et_price
        etQuantity = dialogView.et_quantity
        etDescription = dialogView.et_description
        cbPurchased = dialogView.item_purchased

        var categoryAdapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.category_array,
            android.R.layout.simple_spinner_item
        )
        categoryAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerCategory.adapter = categoryAdapter

        dialogBuilder.setView(dialogView)

        val arguments = this.arguments

        // if edit mode
        if (arguments != null && arguments.containsKey(ScrollingActivity.KEY_EDIT)) {
            val shoppingItem = arguments.getSerializable(ScrollingActivity.KEY_EDIT) as Item

            etItemName.setText(shoppingItem.itemName)
            spinnerCategory.setSelection(shoppingItem.category)
            etItemPrice.setText(shoppingItem.itemPrice.toString())
            etQuantity.setText(shoppingItem.quantity.toString())
            etDescription.setText(shoppingItem.description)
            cbPurchased.isChecked = shoppingItem.purchased

            dialogBuilder.setTitle(getString(R.string.edit_dialog))
        }

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
            if (etItemName.text.isNotEmpty()) {
                if (etItemPrice.text.isEmpty()){
                    etItemPrice.setText(getString(R.string.default_price))
                }
                if (etQuantity.text.isEmpty()){
                    etQuantity.setText(getString(R.string.default_quantity))
                }
                if (etDescription.text.isEmpty()){
                    etDescription.setText(getString(R.string.default_description))
                }

                val arguments = this.arguments
                // IF EDIT MODE
                if (arguments != null && arguments.containsKey(ScrollingActivity.KEY_EDIT)) {
                    handleItemEdit()
                } else {
                    handleItemCreate()
                }

                dialog!!.dismiss()
            } else {
                etItemName.error = getString(R.string.et_warn)
            }
        }
    }

    private fun handleItemCreate() {
        itemHandler.itemCreated(
            Item(
                null,
                spinnerCategory.selectedItemPosition,
                etItemName.text.toString(),
                etItemPrice.text.toString().toLong(),
                etQuantity.text.toString().toLong(),
                etDescription.text.toString(),
                cbPurchased.isChecked
            )
        )
    }

    private fun handleItemEdit() {
        val itemToEdit = arguments?.getSerializable(
            ScrollingActivity.KEY_EDIT
        ) as Item
        itemToEdit.itemName = etItemName.text.toString()
        itemToEdit.category = spinnerCategory.selectedItemPosition
        itemToEdit.itemPrice = etItemPrice.text.toString().toLong()
        itemToEdit.quantity = etQuantity.text.toString().toLong()
        itemToEdit.description = etDescription.text.toString()
        itemToEdit.purchased = cbPurchased.isChecked

        itemHandler.itemUpdated(itemToEdit)
    }
}