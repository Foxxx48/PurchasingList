package com.fox.purchasinglist.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.fox.purchasinglist.data.PurchaseListRepositoryImpl
import com.fox.purchasinglist.domain.DeletePurchaseItemUseCase
import com.fox.purchasinglist.domain.EditPurchaseItemUseCase
import com.fox.purchasinglist.domain.GetListPurchaseItemUseCase
import com.fox.purchasinglist.domain.PurchaseItem

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = PurchaseListRepositoryImpl(application)

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