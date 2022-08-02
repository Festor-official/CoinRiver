package com.example.coinriver.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey
    @ColumnInfo(name = "category_name")
    var category_name:String,
    @ColumnInfo(name = "category_image")
    var category_image:String,
    @ColumnInfo(name = "category_sum")
    var category_sum:Float,
    @ColumnInfo(name = "category_amount_of_operation")
    var category_amount_of_operation:Int,
)
