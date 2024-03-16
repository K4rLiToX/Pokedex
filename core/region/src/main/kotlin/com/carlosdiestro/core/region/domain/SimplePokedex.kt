package com.carlosdiestro.core.region.domain

import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.common.models.Name

data class SimplePokedex(
    val id: ID,
    val regionId: ID,
    val name: Name,
)
