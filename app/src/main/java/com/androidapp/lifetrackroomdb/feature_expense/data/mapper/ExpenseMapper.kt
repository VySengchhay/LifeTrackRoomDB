package com.androidapp.lifetrackroomdb.feature_expense.data.mapper

import com.androidapp.lifetrackroomdb.feature_expense.data.local.ExpenseEntity
import com.androidapp.lifetrackroomdb.feature_expense.domain.model.ExpenseModel

fun ExpenseEntity.toModel(): ExpenseModel {
    return ExpenseModel(
        id = id,
        title = title,
        amount = amount,
        category = category,
        note = note,
        createdAt = createdAt,
    )
}

fun ExpenseModel.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = id,
        title = title,
        amount = amount,
        category = category,
        note = note,
        createdAt = createdAt,
    )
}