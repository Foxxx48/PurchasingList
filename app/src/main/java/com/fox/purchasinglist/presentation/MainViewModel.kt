package com.fox.purchasinglist.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fox.purchasinglist.domain.DeletePurchaseItemUseCase
import com.fox.purchasinglist.domain.EditPurchaseItemUseCase
import com.fox.purchasinglist.domain.GetListPurchaseItemUseCase
import com.fox.purchasinglist.domain.PurchaseItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getListPurchaseItemUseCase: GetListPurchaseItemUseCase,
    private val editPurchaseItemUseCase: EditPurchaseItemUseCase,
    private val deletePurchaseItemUseCase: DeletePurchaseItemUseCase
) : ViewModel() {

    val purchaseList = getListPurchaseItemUseCase()

    fun deletePurchaseItem(purchaseItem: PurchaseItem) {
        viewModelScope.launch {
            deletePurchaseItemUseCase(purchaseItem)
        }
    }

    fun changeEnableState(purchaseItem: PurchaseItem) {
        viewModelScope.launch {
            val newItem = purchaseItem.copy(enabled = !purchaseItem.enabled)
            editPurchaseItemUseCase(newItem)
        }
    }

    fun printLog(purchaseItem: PurchaseItem) {
        Log.d("fun printLog", "$purchaseItem")
    }
}