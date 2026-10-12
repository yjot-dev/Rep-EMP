package com.yjotdev.empprimaria.data.local.mapper

import com.yjotdev.empprimaria.data.local.entity.UserEntity
import com.yjotdev.empprimaria.domain.model.UserModel

fun UserEntity.toDomain() = UserModel(
    id = this.id,
    name = this.name,
    email = this.email,
    password = this.password,
    photo = this.photo,
    isInvited = this.isInvited,
    isInWhiteList = this.isInWhiteList
)

fun UserModel.toBD() = UserEntity(
    id = this.id,
    name = this.name,
    email = this.email,
    password = this.password,
    photo = this.photo,
    isInvited = this.isInvited,
    isInWhiteList = this.isInWhiteList
)