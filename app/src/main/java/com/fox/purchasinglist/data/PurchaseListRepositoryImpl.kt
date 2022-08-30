package com.fox.purchasinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fox.purchasinglist.domain.PurchaseItem
import com.fox.purchasinglist.domain.PurchaseListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object PurchaseListRepositoryImpl: PurchaseListRepository {

    private val purchaseListLD = MutableLiveData<List<PurchaseItem>>()

    private val purchaseList = sortedSetOf<PurchaseItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrementId = 0

    init {
        for (i in 0 until 20) {
            val item = PurchaseItem("Name $i", i, Random.nextBoolean())
            addPurchaseItem(item)
        }
    }


    override fun addPurchaseItem(purchaseItem: PurchaseItem) {
        if (purchaseItem.id == PurchaseItem.UNDEFINED_ID) {
            purchaseItem.id = autoIncrementId++
        }
        purchaseList.add(purchaseItem)
        updateList()
    }

    override fun deletePurchaseItem(purchaseItem: PurchaseItem) {
        purchaseList.remove(purchaseItem)
        updateList()
    }

    override fun editPurchaseItem(purchaseItem: PurchaseItem) {
        val oldElement = getPurchaseItem(purchaseItem.id)
        purchaseList.remove(oldElement)
        addPurchaseItem(purchaseItem)
    }

    override fun getPurchaseItem(purchaseItemId: Int): PurchaseItem {
       return purchaseList.find {
            it.id == purchaseItemId
        } ?: throw RuntimeException("Element with $purchaseItemId not found")
    }

    override fun getListPurchaseItem(): LiveData<List<PurchaseItem>> {
        return purchaseListLD
    }

    private fun updateList() {
        purchaseListLD.value = purchaseList.toList()
    }
}