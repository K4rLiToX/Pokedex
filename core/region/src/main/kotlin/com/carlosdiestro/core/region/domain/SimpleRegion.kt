package com.carlosdiestro.core.region.domain

import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.common.models.Name

data class SimpleRegion(
    val id: ID,
    val name: Name,
)