package com.carlosdiestro.core.region

import com.carlosdiestro.core.region.domain.ID
import com.carlosdiestro.core.region.domain.Name
import com.carlosdiestro.core.region.domain.Region

object RegionsData {
    val regions: List<Region> = listOf(
        Region(
            id = ID(1),
            name = Name("kanto")
        ),
        Region(
            id = ID(2),
            name = Name("johto")
        ),
        Region(
            id = ID(3),
            name = Name("hoenn")
        ),
        Region(
            id = ID(4),
            name = Name("sinnoh")
        ),
        Region(
            id = ID(5),
            name = Name("unova")
        ),
        Region(
            id = ID(6),
            name = Name("kalos")
        ),
        Region(
            id = ID(7),
            name = Name("galar")
        ),
        Region(
            id = ID(8),
            name = Name("hisui")
        ),
        Region(
            id = ID(9),
            name = Name("paldea")
        ),
    )
}