package com.fox.purchasinglist.data.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PurchaseListDao {

    @Query("SELECT * FROM purchase_list" )
    fun getPurchaseList(): LiveData<List<PurchaseItemDbModel>>

    @Query("SELECT * FROM purchase_list" )
    fun getPurchaseListCursor(): Cursor

    @Insert(entity = PurchaseItemDbModel::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPurchaseItem(purchaseItemDbModel: PurchaseItemDbModel)

    @Insert(entity = PurchaseItemDbModel::class, onConflict = OnConflictStrategy.REPLACE)
    fun addPurchaseItemSync(purchaseItemDbModel: PurchaseItemDbModel)

    @Query("DELETE FROM purchase_list WHERE id=:purchaseItemId")
    suspend fun deletePurchaseItem(purchaseItemId: Int)

    @Query("DELETE FROM purchase_list WHERE id=:purchaseItemId")
     fun deletePurchaseItemSync(purchaseItemId: Int): Int

    @Query("SELECT * FROM purchase_list WHERE id=:purchaseItemId LIMIT 1")
    suspend fun getPurchaseItem(purchaseItemId: Int): PurchaseItemDbModel
}