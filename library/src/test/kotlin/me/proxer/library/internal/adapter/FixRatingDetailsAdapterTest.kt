package me.proxer.library.internal.adapter

import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import io.mockk.every
import io.mockk.mockk
import me.proxer.library.entity.info.RatingDetails
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

/**
 * @author Ruben Gees
 */
class FixRatingDetailsAdapterTest {

    private val jsonReader = mockk<JsonReader>()
    private val adapter = FixRatingDetailsAdapter()
    private val delegate = Moshi.Builder().build().adapter(RatingDetails::class.java)

    @Test
    fun testFromJson() {
        every { jsonReader.nextString() } returns """
            {"genre":"5","story":"5","animation":"5","characters":"5","music":"5"}
        """.trimIndent().replace("\n", "")

        adapter.fromJson(jsonReader, delegate) shouldBeEqualTo RatingDetails(
            genre = 5,
            story = 5,
            animation = 5,
            characters = 5,
            music = 5
        )
    }

    @Test
    fun testFromJsonPartial() {
        every { jsonReader.nextString() } returns """{"genre":"5","story":"5","animation":"5"}"""

        adapter.fromJson(jsonReader, delegate) shouldBeEqualTo RatingDetails(
            genre = 5,
            story = 5,
            animation = 5,
            characters = 0,
            music = 0
        )
    }

    @Test
    fun testFromJsonWeirdArray() {
        every { jsonReader.nextString() } returns "[]"

        adapter.fromJson(jsonReader, delegate) shouldBeEqualTo RatingDetails(
            genre = 0,
            story = 0,
            animation = 0,
            characters = 0,
            music = 0
        )
    }

    @Test
    fun testFromJsonEmpty() {
        every { jsonReader.nextString() } returns ""

        adapter.fromJson(jsonReader, delegate) shouldBeEqualTo RatingDetails(
            genre = 0,
            story = 0,
            animation = 0,
            characters = 0,
            music = 0
        )
    }

    @Test
    fun testFromJsonInvalid() {
        every { jsonReader.nextString() } returns """{"invalid":"invalid"}"""

        adapter.fromJson(jsonReader, delegate) shouldBeEqualTo RatingDetails(
            genre = 0,
            story = 0,
            animation = 0,
            characters = 0,
            music = 0
        )
    }
}
