package com.cloudemployee.baseproject.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("items")
    val items: List<User> = emptyList(),
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("quota_max")
    val quotaMax: Int,
    @SerializedName("quota_remaining")
    val quotaRemaining: Int,
)

data class User(
    @SerializedName("account_id")
    val accountId: Long,
    @SerializedName("reputation")
    val reputation: Int,
    @SerializedName("creation_date")
    val creationDate: Long,
    @SerializedName("profile_image")
    val profileImage: String,
    @SerializedName("display_name")
    val displayName: String,
)

