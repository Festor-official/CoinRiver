package com.example.coinriver.repository

import com.example.coinriver.dao.ExpensesDao
import com.example.coinriver.data.Expenses

class ExpensesRepository(private val expensesDao:ExpensesDao) {

    val allExpenses:List<Expenses> = expensesDao.get()

    suspend fun insert(expenses:Expenses){
        expensesDao.insert(expenses)
    }

}