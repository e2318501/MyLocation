package com.github.tsuoihito.mylocation.model

import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable

class Point : ConfigurationSerializable {
    val name: String
    val location: Location
    val materialString: String

    constructor(name: String, location: Location, materialString: String) {
        this.name = name
        this.location = location
        this.materialString = materialString
    }

    constructor(args: MutableMap<String, Any>) {
        this.name = args["name"] as String
        this.location = args["location"] as Location
        this.materialString = args["materialString"] as String
    }

    override fun serialize(): MutableMap<String, Any> {
        val serialized: MutableMap<String, Any> = HashMap()
        serialized["name"] = name
        serialized["location"] = location
        serialized["materialString"] = materialString
        return serialized
    }
}
