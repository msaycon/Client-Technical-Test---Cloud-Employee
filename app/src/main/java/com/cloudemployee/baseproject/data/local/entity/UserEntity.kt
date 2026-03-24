package com.cloudemployee.baseproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val accountId: Long,
    val reputation: Int,
    val creationDate: Long,
    val profileImage: String,
    val displayName: String,
)
