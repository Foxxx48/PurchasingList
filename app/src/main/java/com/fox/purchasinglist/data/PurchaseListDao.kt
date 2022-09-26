package com.fox.purchasinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PurchaseListDao {

    @Query("SELECT * FROM purchase_list" )
    fun getPurchaseList(): LiveData<List<PurchaseItemDbModel>>

    @Insert(entity = PurchaseItemDbModel::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPurchaseItem(purchaseItemDbModel: PurchaseItemDbModel)

    @Query("DELETE FROM purchase_list WHERE id=:purchaseItemId")
    suspend fun deletePurchaseItem(purchaseItemId: Int)

    @Query("SELECT * FROM purchase_list WHERE id=:purchaseItemId LIMIT 1")
    suspend fun getPurchaseItem(purchaseItemId: Int): PurchaseItemDbModel
}