package com.example.luasapp.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "stopInfo", strict = false)
data class StopInfo(
    @field:Attribute(name = "created", required = false)
    var created: String = "",
    @field:Attribute(name = "stop", required = false)
    var stop: String = "",
    @field:Attribute(name = "stopAbv", required = false)
    var stopAbv: String = "",
    @field:Element(name = "message")
    var message: String = "",
    @field:ElementList(name = "direction", inline = true)
    var directionInfos: MutableList<DirectionInfo> = mutableListOf()
)