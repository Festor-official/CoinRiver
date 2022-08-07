package com.example.coinriver.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.coinriver.R
import com.example.coinriver.viewmodel.ExpensesViewModel
import com.example.coinriver.views.CircleParts
import dagger.hilt.android.AndroidEntryPoint


var tag = "MainActivity"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val expensesViewModel: ExpensesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        var date = Calendar.getInstance().getTime()
//        val sdf = SimpleDateFormat("yyyy.MM.dd_HHmmss", Locale.getDefault())
//        val currentDateAndTime: String = sdf.format(date)
//        Log.v(tag,currentDateAndTime)
//        var spending = arrayListOf<Expenses>(
//            Expenses(0,"food",200, Date(),false),
//            Expenses(1,"food",100, Date(),false),
//            Expenses(2,"food",29, Date(),false),
//            Expenses(3,"food",200, Date(),false),
//            Expenses(4,"food",70, Date(),false)
//        )



        var circleParts = findViewById<CircleParts>(R.id.circleParts)
        circleParts.setSpending(expensesViewModel.allExpenses)
        Log.v(tag,expensesViewModel.allExpenses.toString())

    }
}

//make stastic cycrler clicable by category
//




//category_food"
//category_clothes"
//category_sport"
//category_entertaining"
//category_household_chemicals"
//category_technology"
//category_fast_food"
//category_internet"
//category_others"