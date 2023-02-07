package com.fox.purchasinglist.domain

import javax.inject.Inject

class AddPurchaseItemUseCase @Inject constructor(private val purchaseListRepository: PurchaseListRepository) {
    suspend operator fun invoke(
        purchaseItem: PurchaseItem
    ) = purchaseListRepository.addPurchaseItem(purchaseItem)
}