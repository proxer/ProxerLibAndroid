package me.proxer.library.api;

import com.squareup.moshi.JsonDataException;
import me.proxer.library.entity.manga.Page;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Ruben Gees
 */
class PageAdapterTest {

    private PageAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new PageAdapter();
    }

    @Test
    void testFromJson() {
        Assertions.assertThat(adapter.fromJson(new String[][]{
                new String[]{"a", "1", "2"},
                new String[]{"b", "5", "3"}
        })).containsExactly(new Page("a", 1, 2), new Page("b", 5, 3));
    }

    @Test
    void testFromJsonInvalidSize() {
        assertThatExceptionOfType(JsonDataException.class).isThrownBy(() -> adapter.fromJson(new String[][]{
                new String[]{"a", "1", "2"},
                new String[0]
        }));
    }

    @Test
    void testFromJsonNoNumber() {
        assertThatExceptionOfType(NumberFormatException.class).isThrownBy(() -> adapter.fromJson(new String[][]{
                new String[]{"a", "invalid", "2"}
        }));
    }
}
