package com.cloudemployee.baseproject.domain.model

data class User(
    val accountId: Long,
    val reputation: Int,
    val creationDate: Long,
    val profileImage: String,
    val displayName: String,
)
