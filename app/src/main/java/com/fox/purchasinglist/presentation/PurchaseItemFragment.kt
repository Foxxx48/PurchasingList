package com.fox.purchasinglist.presentation

import android.content.ContentValues
import android.content.Context
import android.net.Uri
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
import com.fox.purchasinglist.PurchaseItemApp
import com.fox.purchasinglist.R
import com.fox.purchasinglist.databinding.FragmentPurchaseItemBinding
import com.fox.purchasinglist.domain.PurchaseItem
import com.google.android.material.textfield.TextInputLayout
import javax.inject.Inject
import kotlin.concurrent.thread


class PurchaseItemFragment : Fragment() {

    private var _binding: FragmentPurchaseItemBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentPurchaseItemBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {

        ViewModelProvider(this, viewModelFactory)[PurchaseItemViewModel::class.java]
    }


    private val myComponent by lazy {
        (requireActivity().application as PurchaseItemApp).myComponent
    }


    lateinit var onEditingFinishListener: OnEditingFinishListener

    private var screenMode = MODE_UNKNOWN
    private var purchaseItemId = PurchaseItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        myComponent.inject(this)
        super.onAttach(context)
        if (context is OnEditingFinishListener) {
            onEditingFinishListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("PurchaseItemFragment", "onCreate")
        parseParams()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPurchaseItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            binding.tilCount.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            binding.tilName.error = message
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishListener?.onEditingFinished()
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
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etCount.addTextChangedListener(object : TextWatcher {
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
            binding.etName.setText(it.name)
            binding.etCount.setText(it.count.toString())

        }

        binding.btnSave.setOnClickListener {
            thread {
                context?.contentResolver?.update(
                    Uri.parse("content://com.fox.purchasinglist/purchase_list"),
                    ContentValues().apply {
                        put("id", purchaseItemId )
                        put("name", binding.etName.text?.toString())
                        put("count", binding.etCount.text?.toString()?.toInt())
                        put("enabled", true)
                    },
                    null,
                    arrayOf(purchaseItemId.toString())
                    )
            }
        }
    }
        private fun launchAddMode() {
            binding.btnSave.setOnClickListener {

                thread {
                    context?.contentResolver?.insert(
                        Uri.parse("content://com.fox.purchasinglist/purchase_list"),
                        ContentValues().apply {
                            put("id", 0)
                            put("name", binding.etName.text?.toString())
                            put("count", binding.etCount.text?.toString()?.toInt())
                            put("enabled", true)
                        }
                    )
                }
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

        interface OnEditingFinishListener {
            fun onEditingFinished()
        }

        override fun onDestroy() {
            super.onDestroy()
            _binding = null
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