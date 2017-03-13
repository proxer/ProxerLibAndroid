package com.proxerme.library.api;

import com.proxerme.library.entitiy.manga.Page;
import com.squareup.moshi.FromJson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Gees
 */
final class PageAdapter {

    @FromJson
    List<Page> fromJson(final String[][] json) {
        List<Page> result = new ArrayList<>();

        for (String[] jsonPage : json) {
            result.add(new Page(jsonPage[0], Integer.parseInt(jsonPage[1]), Integer.parseInt(jsonPage[2])));
        }

        return result;
    }
}
