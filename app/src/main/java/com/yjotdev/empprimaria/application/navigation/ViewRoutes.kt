package com.yjotdev.empprimaria.application.navigation

import androidx.annotation.StringRes
import com.yjotdev.empprimaria.R

enum class ViewRoutes(@StringRes val idTitle: Int){
    Login(idTitle = 0),
    Register(idTitle = R.string.button_register),
    RecoverKey(idTitle = R.string.button_recover_key),
    Menu(idTitle = 0)
}