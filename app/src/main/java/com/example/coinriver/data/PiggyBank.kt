package com.example.coinriver.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.util.*

data class PiggyBank(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "income_id")
    val income_id:Int,

    @ColumnInfo (name = "income_sum")
    val income_sum:Long,

    @ColumnInfo (name = "income_date")
    val income_date: Date,

    @ColumnInfo (name = "piggy_bank_sum")
    val piggy_bank_sum:Long,

    )