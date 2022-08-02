package com.example.coinriver.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.util.Date

data class Expenses(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "expenses_id")
    val expenses_id:Int,

    @ColumnInfo(name = "expenses_category")
    val expenses_category:String,

    @ColumnInfo(name = "expenses_sum")
    val expenses_sum:Long,

    @ColumnInfo(name = "expenses_date")
    val expenses_date:Date,

    @ColumnInfo(name = "expenses_took_from_piggy_bank")
    val expenses_took_from_piggy_bank:Boolean,






)
