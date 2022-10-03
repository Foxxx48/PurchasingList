package com.fox.purchasinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.fox.purchasinglist.PurchaseItemApp
import com.fox.purchasinglist.R
import com.fox.purchasinglist.domain.PurchaseItem
import javax.inject.Inject

class PurchaseItemActivity : AppCompatActivity(), PurchaseItemFragment.OnEditingFinishListener {

    private var screenMode = MODE_UNKNOWN
    private var purchaseItemId = PurchaseItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_item)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        finish()
    }


    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> PurchaseItemFragment.newInstanceEditItem(purchaseItemId)
            MODE_ADD -> PurchaseItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.purchase_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_PURCHASE_ITEM_ID)) {
                throw RuntimeException("Param purchase item id is absent")
            }
            purchaseItemId = intent.getIntExtra(EXTRA_PURCHASE_ITEM_ID, PurchaseItem.UNDEFINED_ID)
        }
    }


    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_PURCHASE_ITEM_ID = "extra_purchase_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, PurchaseItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, PurchaseItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_PURCHASE_ITEM_ID, shopItemId)
            return intent
        }
    }
}