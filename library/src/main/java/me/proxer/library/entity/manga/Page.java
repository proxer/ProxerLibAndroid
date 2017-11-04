package me.proxer.library.entity.manga;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Entity representing a single page from a {@link Chapter}.
 *
 * @author Ruben Gees
 */
@Value
@EqualsAndHashCode(onParam = @__({@Nullable}))
public class Page {

    /**
     * Returns the name of the page. To be used for retrieving the url.
     */
    private String name;

    /**
     * Returns the height of the page.
     */
    private int height;

    /**
     * Returns the width of the uploader if present.
     */
    private int width;
}