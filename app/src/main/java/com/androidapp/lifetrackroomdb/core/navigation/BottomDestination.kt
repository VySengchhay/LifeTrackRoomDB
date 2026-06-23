package com.androidapp.lifetrackroomdb.core.navigation

import androidx.annotation.DrawableRes
import com.androidapp.lifetrackroomdb.R

data class BottomDestination(
    val label: String,
    @param:DrawableRes val icon: Int,
    val route: AppRoute,
)

val bottomDestinations = listOf(
    BottomDestination(
        label = "Dashboard",
        icon = R.drawable.ic_dashboard,
        route = AppRoute.Dashboard,
    ),
    BottomDestination(
        label = "Tasks",
        icon = R.drawable.ic_task,
        route = AppRoute.Tasks,
    ),
    BottomDestination(
        label = "Expenses",
        icon = R.drawable.ic_money_bag,
        route = AppRoute.Expenses,
    ),
)
