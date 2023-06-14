package com.example.beyondcopy.classes

import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener

data class ToolbarMenu (
    val clear: Boolean = false,
    @MenuRes val menuId: Int = 0,
    val onMenuItemClickListener: OnMenuItemClickListener = OnMenuItemClickListener{return@OnMenuItemClickListener true},
    )