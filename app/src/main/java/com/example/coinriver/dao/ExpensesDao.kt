package com.example.coinriver.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinriver.data.Expenses

@Dao
interface ExpensesDao {

    @Query("SELECT * from expenses_table")
    fun get(): List<Expenses>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expenses: Expenses)

    @Query("DELETE from expenses_table ")
    fun clear()


}