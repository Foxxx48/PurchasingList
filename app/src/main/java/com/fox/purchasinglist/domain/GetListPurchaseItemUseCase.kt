package com.fox.purchasinglist.domain

import androidx.lifecycle.LiveData

class GetListPurchaseItemUseCase(private val purchaseListRepository: PurchaseListRepository) {

    fun getListPurchase(): LiveData<List<PurchaseItem>> {
        return purchaseListRepository.getListPurchaseItem()
    }
}