package com.footiestats.statsengine.entities.engine.enums

import org.neo4j.ogm.typeconversion.AttributeConverter
import java.lang.Exception

class StringToGenderConverter : AttributeConverter<Gender, String> {

    override fun toGraphProperty(from: Gender?): String? {
        if (from == null) return null

        return from.name.toUpperCase()
    }

    override fun toEntityAttribute(from: String?): Gender? {
        if (from == null) return null

        return try {
            Gender.valueOf(from)
        } catch (e: Exception) {
            null
        }
    }
}