package com.androidapp.lifetrackroomdb.core.di

import android.content.Context
import androidx.room.Room
import com.androidapp.lifetrackroomdb.core.database.LifeTrackDatabase
import com.androidapp.lifetrackroomdb.feature_expense.data.local.ExpenseDao
import com.androidapp.lifetrackroomdb.feature_task.data.local.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLifeTrackDatabase(
        @ApplicationContext context: Context
    ): LifeTrackDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = LifeTrackDatabase::class.java,
            name = "lifetrack_db"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: LifeTrackDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    fun provideExpenseDao(database: LifeTrackDatabase): ExpenseDao {
        return database.expenseDao()
    }
}