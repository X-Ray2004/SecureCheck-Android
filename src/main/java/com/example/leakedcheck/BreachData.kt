package com.example.leakedcheck

data class LeakCheckResponse(
    val success: Boolean,
    val found: Int,
    val fields: List<String>?,
    val sources: List<SourceData>?
)

data class SourceData(
    val name: String,
    val date: String
)