package com.fox.purchasinglist.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fox.purchasinglist.data.PurchaseListRepositoryImpl
import com.fox.purchasinglist.domain.DeletePurchaseItemUseCase
import com.fox.purchasinglist.domain.EditPurchaseItemUseCase
import com.fox.purchasinglist.domain.GetListPurchaseItemUseCase
import com.fox.purchasinglist.domain.PurchaseItem

class MainViewModel: ViewModel() {

    private val repository = PurchaseListRepositoryImpl

    private val getListPurchaseItemUseCase = GetListPurchaseItemUseCase(repository)
    private val editPurchaseItemUseCase = EditPurchaseItemUseCase(repository)
    private val deletePurchaseItemUseCase = DeletePurchaseItemUseCase(repository)

//    private val _purchaseList = MutableLiveData<List<PurchaseItem>>()
//    val purchaseList: LiveData<List<PurchaseItem>> get() = _purchaseList

    val purchaseList = getListPurchaseItemUseCase.getListPurchase()

    fun deletePurchaseItem(purchaseItem: PurchaseItem) {
        deletePurchaseItemUseCase.deletePurchase(purchaseItem)

    }

    fun changeEnableState(purchaseItem: PurchaseItem) {
        val newItem = purchaseItem.copy(enabled = !purchaseItem.enabled)
        editPurchaseItemUseCase.editPurchase(newItem)

    }

    fun printLog(purchaseItem: PurchaseItem){
        Log.d("fun printLog", "$purchaseItem")
    }



}