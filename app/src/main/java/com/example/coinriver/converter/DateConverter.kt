package com.example.coinriver.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromDateToGson(date: Date?):String?{
        val type = object: TypeToken<Date>() {}.type
        return Gson().toJson(date,type)

    }

    @TypeConverter
    fun fromGsonToDate(dateSting:String?):Date?{
        val type  =object: TypeToken<Date>() {}.type
        return Gson().fromJson<Date>(dateSting,type)
    }

}


















