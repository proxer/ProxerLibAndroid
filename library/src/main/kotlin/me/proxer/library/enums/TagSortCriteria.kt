package me.proxer.library.enums

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Enum holding the available sort options of the tag search.
 *
 * @author Ruben Gees
 */
@JsonClass(generateAdapter = false)
enum class TagSortCriteria {

    @Json(name = "id")
    ID,

    @Json(name = "type")
    TYPE,

    @Json(name = "tag")
    NAME,

    @Json(name = "description")
    DESCRIPTION,

    @Json(name = "subtype")
    SUBTYPE
}
