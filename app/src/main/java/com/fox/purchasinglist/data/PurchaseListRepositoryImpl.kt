package com.fox.purchasinglist.data

import com.fox.purchasinglist.domain.PurchaseItem
import com.fox.purchasinglist.domain.PurchaseListRepository
import java.lang.RuntimeException

object PurchaseListRepositoryImpl: PurchaseListRepository {

    private val purchaseList = mutableListOf<PurchaseItem>()

    private var autoIncrementId = 0

    override fun addPurchaseItem(purchaseItem: PurchaseItem) {
        if (purchaseItem.id == PurchaseItem.UNDEFINED_ID) {
            purchaseItem.id = autoIncrementId++
        }
        purchaseList.add(purchaseItem)
    }

    override fun deletePurchaseItem(purchaseItem: PurchaseItem) {
        purchaseList.remove(purchaseItem)
    }

    override fun editPurchaseItem(purchaseItem: PurchaseItem) {
        val oldElement = getPurchaseItem(purchaseItem.id)
        deletePurchaseItem(oldElement)
        addPurchaseItem(purchaseItem)
    }

    override fun getPurchaseItem(purchaseItemId: Int): PurchaseItem {
       return purchaseList.find {
            it.id == purchaseItemId
        } ?: throw RuntimeException("Element with $purchaseItemId not found")
    }

    override fun getListPurchaseItem(): List<PurchaseItem> {
        return purchaseList.toList()
    }
}