package com.cloudemployee.baseproject.data.mapper

import com.cloudemployee.baseproject.data.local.entity.UserEntity
import com.cloudemployee.baseproject.data.remote.dto.User as RemoteUser
import com.cloudemployee.baseproject.domain.model.User

fun RemoteUser.toEntity(): UserEntity = UserEntity(
    accountId = accountId,
    reputation = reputation,
    creationDate = creationDate,
    profileImage = profileImage,
    displayName = displayName,
)

fun UserEntity.toDomain(): User = User(
    accountId = accountId,
    reputation = reputation,
    creationDate = creationDate,
    profileImage = profileImage,
    displayName = displayName,
)
