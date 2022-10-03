package com.fox.purchasinglist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetListPurchaseItemUseCase @Inject constructor(private val purchaseListRepository: PurchaseListRepository) {
    operator fun invoke() = purchaseListRepository.getListPurchaseItem()
}