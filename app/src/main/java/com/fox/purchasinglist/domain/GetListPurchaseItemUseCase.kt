package com.fox.purchasinglist.domain

class GetListPurchaseItemUseCase(private val purchaseListRepository: PurchaseListRepository) {

    fun getListPurchase(): List<PurchaseItem> {
        return purchaseListRepository.getListPurchaseItem()
    }
}