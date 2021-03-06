package me.proxer.library.enums

import com.serjltt.moshi.adapters.FallbackEnum
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import me.proxer.library.entity.messenger.Message

/**
 * Enum holding the possible actions of a [Message].
 *
 * @author Ruben Gees
 */
@JsonClass(generateAdapter = false)
@FallbackEnum(name = "NONE")
enum class MessageAction {

    @Json(name = "")
    NONE,

    @Json(name = "addUser")
    ADD_USER,

    @Json(name = "removeUser")
    REMOVE_USER,

    @Json(name = "setLeader")
    SET_LEADER,

    @Json(name = "setTopic")
    SET_TOPIC,

    @Json(name = "exit")
    EXIT
}
