package com.example.luasapp.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "direction", strict = false)
data class DirectionInfo(
    @field:Attribute(name = "name", required = false)
    var name: String = "",
    @field:ElementList(name = "tram", inline = true)
    var tramInfos: MutableList<TramInfo> = mutableListOf()
)