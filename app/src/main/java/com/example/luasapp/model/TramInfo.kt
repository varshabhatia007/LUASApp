package com.example.luasapp.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "tram", strict = false)
data class TramInfo(
    @field:Attribute(name = "dueMins", required = false)
    var dueMins: String = "",
    @field:Attribute(name = "destination", required = false)
    var destination: String = ""
)