package com.example.coinriver.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinriver.data.Expenses
import com.example.coinriver.database.ExpensesDatabase
import com.example.coinriver.repository.ExpensesRepository

class ExpensesViewModel(application: Application):AndroidViewModel(application) {
        private val repository:ExpensesRepository
        val allExpenses:List<Expenses>

        init {
            val expensesDao = ExpensesDatabase.getDatabase(application,viewModelScope).expensesDao()
            repository = ExpensesRepository(expensesDao)
            allExpenses = repository.allExpenses
        }

}