package com.kevin.publicapis.model


data class ApiResponseItem(
    val count: Int,
    val entries: List<Entry>
)

data class Entry(
    val API: String,
    val Auth: String,
    val Category: String,
    val Cors: String,
    val Description: String,
    val HTTPS: Boolean,
    val Link: String
)