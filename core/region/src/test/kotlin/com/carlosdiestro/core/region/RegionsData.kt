package com.carlosdiestro.core.region

import com.carlosdiestro.core.region.domain.ID
import com.carlosdiestro.core.region.domain.Name
import com.carlosdiestro.core.region.domain.SimpleRegion

object RegionsData {
    val regions: List<SimpleRegion> = listOf(
        SimpleRegion(
            id = ID(1),
            name = Name("kanto")
        ),
        SimpleRegion(
            id = ID(2),
            name = Name("johto")
        ),
        SimpleRegion(
            id = ID(3),
            name = Name("hoenn")
        ),
        SimpleRegion(
            id = ID(4),
            name = Name("sinnoh")
        ),
        SimpleRegion(
            id = ID(5),
            name = Name("unova")
        ),
        SimpleRegion(
            id = ID(6),
            name = Name("kalos")
        ),
        SimpleRegion(
            id = ID(7),
            name = Name("galar")
        ),
        SimpleRegion(
            id = ID(8),
            name = Name("hisui")
        ),
        SimpleRegion(
            id = ID(9),
            name = Name("paldea")
        ),
    )
}