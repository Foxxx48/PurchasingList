package com.fox.purchasinglist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fox.purchasinglist.R
import com.fox.purchasinglist.domain.PurchaseItem
import com.google.android.material.textfield.TextInputLayout


class PurchaseItemFragment : Fragment() {

    private lateinit var viewModel: PurchaseItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button

    private var screenMode = MODE_UNKNOWN
    private var purchaseItemId = PurchaseItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("PurchaseItemFragment", "onCreate")
        parseParams()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_purchase_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[PurchaseItemViewModel::class.java]
        initViews(view)
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun launchRightMode() {
        println("LaunchRightMode($screenMode)")
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }

    }

    private fun addTextChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchEditMode() {
        viewModel.getPurchaseItem(purchaseItemId)
        println("LaunchEditMode()")

        viewModel.purchaseItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            println("PurchaseItem Name ${it.name}")
            etCount.setText(it.count.toString())
            println("PurchaseItem Count ${it.count}")
        }
        buttonSave.setOnClickListener {
            viewModel.editPurchaseItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun launchAddMode() {
        println("LaunchAddMode()")
        buttonSave.setOnClickListener {
            viewModel.addPurchaseItem(etName.text?.toString(), etCount.text?.toString())
        }
    }


    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Parameters of screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        println("parsParams mode = $mode")
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(PURCHASE_ITEM_ID)) {
                throw RuntimeException("Parameters of purchase item id is absent")
            }
            purchaseItemId = args.getInt(PURCHASE_ITEM_ID, PurchaseItem.UNDEFINED_ID)
            println("parseParams purchaseItemId  = $purchaseItemId")
        }

    }


    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.btn_save)
    }

    companion object {
        private val SCREEN_MODE = "extra_mode"
        private val PURCHASE_ITEM_ID = "extra_purchase_item_id"
        private val MODE_EDIT = "mode_edit"
        private val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): PurchaseItemFragment {
            return PurchaseItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(purchaseItemId: Int): PurchaseItemFragment {
            return PurchaseItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(PURCHASE_ITEM_ID, purchaseItemId)
                    println(purchaseItemId)
                }
            }
        }


    }
}