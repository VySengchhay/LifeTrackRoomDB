package com.androidapp.lifetrackroomdb.feature_expense.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses ORDER BY createdAt DESC")
    fun getExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE id = :expenseId LIMIT 1")
    suspend fun getExpenseById(expenseId: Int): ExpenseEntity

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Query("DELETE FROM expenses WHERE id = :expenseId")
    suspend fun deleteExpense(expenseId: Int)

    @Query(
        """
        SELECT * FROM expenses
        WHERE (:search IS NULL OR title LIKE '%' || :search || '%')
        AND (:category IS NULL OR category = :category)
        AND (:minAmount IS NULL OR amount >= :minAmount)
        AND (:maxAmount IS NULL OR amount <= :maxAmount)
        ORDER BY createdAt DESC
        """
    )
    fun filterExpenses(
        search: String?,
        category: String?,
        minAmount: Double?,
        maxAmount: Double?
    ): Flow<List<ExpenseEntity>>
}