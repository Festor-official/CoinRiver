package com.example.coinriver.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.coinriver.converter.DateConverter
import com.example.coinriver.dao.ExpensesDao
import com.example.coinriver.data.Expenses
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Expenses::class],version = 2, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ExpensesDatabase:RoomDatabase() {

    abstract fun expensesDao(): ExpensesDao

    companion object{
        @Volatile
        private var INSTANCEEXPENSES:ExpensesDatabase? = null
        fun getDatabase(context: Context, scope:CoroutineScope):ExpensesDatabase{
            return INSTANCEEXPENSES?: synchronized(this){
                val instanceExpenses = Room.databaseBuilder(
                    context.applicationContext,
                    ExpensesDatabase::class.java,
                    "expenses_database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().addCallback(ExpensesDatabaseCallback(scope)).build()
                INSTANCEEXPENSES = instanceExpenses
                return instanceExpenses
            }
        }

        private class ExpensesDatabaseCallback(private val scope:CoroutineScope):Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCEEXPENSES?.let {
                    database -> scope.launch(Dispatchers.IO) {
                        populateDatabase(database.expensesDao())
                }
                }
            }
        }

        suspend fun populateDatabase(expensesDao: ExpensesDao){
//            expensesDao.clear()
            expensesDao.insert(Expenses(0,"Food",100, Date(),false))
            expensesDao.insert(Expenses(1,"Food",100, Date(),false))
            expensesDao.insert(Expenses(2,"Food",29, Date(),false))
            expensesDao.insert(Expenses(3,"Tech",1000, Date(),false))
            expensesDao.insert(Expenses(4,"Bus",70, Date(),false))





        }

    }

}












