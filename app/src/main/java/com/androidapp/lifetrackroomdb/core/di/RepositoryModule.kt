package com.androidapp.lifetrackroomdb.core.di

import com.androidapp.lifetrackroomdb.feature_expense.data.repository.ExpenseRepositoryImpl
import com.androidapp.lifetrackroomdb.feature_expense.domain.repository.ExpenseRepository
import com.androidapp.lifetrackroomdb.feature_task.data.repository.TaskRepositoryImpl
import com.androidapp.lifetrackroomdb.feature_task.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    abstract fun bindExpenseRepository(
        expenseRepositoryImpl: ExpenseRepositoryImpl
    ): ExpenseRepository
}