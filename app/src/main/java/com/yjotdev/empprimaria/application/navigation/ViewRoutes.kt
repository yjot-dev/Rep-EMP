package com.yjotdev.empprimaria.application.navigation

import androidx.annotation.StringRes
import com.yjotdev.empprimaria.R

enum class ViewRoutes(@get:StringRes val idTitle: Int){
    Login(idTitle = 0),
    Register(idTitle = R.string.button_register),
    RecoverKey(idTitle = R.string.button_recover_key),
    UserInfo(idTitle = 0),
    Projects(idTitle = 0),
    Opinion(idTitle = 0),
    Units(idTitle = 0),
    Level(idTitle = 0),
    Story(idTitle = 0)
}