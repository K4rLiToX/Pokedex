package com.carlosdiestro.core.region.domain

data class SimpleRegion(
    val id: ID,
    val name: Name
)

@JvmInline
value class ID(val id: Int)

@JvmInline
value class Name(val name: String)