package com.fox.purchasinglist.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.fox.purchasinglist.data.PurchaseListRepositoryImpl
import com.fox.purchasinglist.domain.DeletePurchaseItemUseCase
import com.fox.purchasinglist.domain.EditPurchaseItemUseCase
import com.fox.purchasinglist.domain.GetListPurchaseItemUseCase
import com.fox.purchasinglist.domain.PurchaseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PurchaseListRepositoryImpl(application)

    private val getListPurchaseItemUseCase = GetListPurchaseItemUseCase(repository)
    private val editPurchaseItemUseCase = EditPurchaseItemUseCase(repository)
    private val deletePurchaseItemUseCase = DeletePurchaseItemUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.Default)

//    private val _purchaseList = MutableLiveData<List<PurchaseItem>>()
//    val purchaseList: LiveData<List<PurchaseItem>> get() = _purchaseList

    val purchaseList = getListPurchaseItemUseCase.getListPurchase()

    fun deletePurchaseItem(purchaseItem: PurchaseItem) {
        scope.launch {
            deletePurchaseItemUseCase.deletePurchase(purchaseItem)
        }
    }

    fun changeEnableState(purchaseItem: PurchaseItem) {
        scope.launch {
            val newItem = purchaseItem.copy(enabled = !purchaseItem.enabled)
            editPurchaseItemUseCase.editPurchase(newItem)
        }
    }

    fun printLog(purchaseItem: PurchaseItem) {
        Log.d("fun printLog", "$purchaseItem")
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}