package com.proxerme.library.connection.info.request;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.proxerme.library.connection.ProxerResult;
import com.proxerme.library.connection.info.InfoRequest;
import com.proxerme.library.connection.info.entity.Comment;
import com.proxerme.library.connection.info.result.CommentResult;
import com.proxerme.library.parameters.CommentSortParameter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.ResponseBody;

/**
 * The request to receive {@link Comment}'s of an specific entry.
 *
 * @author Desnoo
 */
public class CommentRequest extends InfoRequest<Comment[]> {

    private static final String ENDPOINT = "comments";

    private static final String ID_PARAMETER = "id";
    private static final String PAGE_PARAMETER = "p";
    private static final String LIMIT_PARAMETER = "limit";
    private static final String SORT_PARAMETER = "sort";

    private static final String EMPTY_DATA_PATTERN = "\"data\":\"(\\[\\])?\"";
    private static final String EMPTY_DATA_REPLACEMENT = "\"data\":{}";
    private static final String START_PATTERN = "\"{";
    private static final String END_PATTERN = "}\"";
    private static final String START_REPLACEMENT = "{";
    private static final String END_REPLACEMENT = "}";
    private static final String ESCAPE_PATTERN = "\\";
    private static final String ESCAPE_REPLACEMENT = "";

    private String id;
    private Integer page;
    private Integer limit;
    private String sortType;

    /**
     * The basic constructor to set the anime/manga id.
     *
     * @param id The id to request the info from.
     */
    public CommentRequest(@NonNull String id) {
        this.id = id;
    }

    /**
     * Set the page to request the comments by the request.
     *
     * @param page The page to get the comments from.
     * @return The modified request.
     */
    public CommentRequest withPage(@IntRange(from = 0) int page) {
        this.page = page;

        return this;
    }

    /**
     * Set the limit of comments to get per page by the request.
     *
     * @param limit The limit of comments to get from a page.
     * @return The modified request.
     */
    public CommentRequest withLimit(@IntRange(from = 0) int limit) {
        this.limit = limit;

        return this;
    }

    /**
     * Set the sort type of request for the comments. This defaults to return newest first.
     *
     * @param sortType The sort type to use.
     * @return The modified request.
     */
    public CommentRequest withSortType(@CommentSortParameter.CommentSort String sortType) {
        this.sortType = sortType;

        return this;
    }

    @Override
    protected ProxerResult<Comment[]> parse(@NonNull Moshi moshi, @NonNull ResponseBody body)
            throws IOException {
        return moshi.adapter(CommentResult.class).fromJson(convertBody(body.string()));
    }

    @NonNull
    private String convertBody(@NonNull String body) {
        String result = body.replaceAll(EMPTY_DATA_PATTERN, EMPTY_DATA_REPLACEMENT);

        int startIndex = result.indexOf(START_PATTERN);
        int endIndex = result.indexOf(END_PATTERN);

        while (startIndex > 0 && endIndex > 0) {
            String convertedSection = result.substring(startIndex, endIndex + END_PATTERN.length())
                    .replace(START_PATTERN, START_REPLACEMENT)
                    .replace(ESCAPE_PATTERN, ESCAPE_REPLACEMENT)
                    .replace(END_PATTERN, END_REPLACEMENT);

            result = result.substring(0, startIndex) + convertedSection +
                    result.substring(endIndex + END_PATTERN.length());
            startIndex = result.indexOf(START_PATTERN, startIndex);
            endIndex = result.indexOf(END_PATTERN, endIndex);
        }

        return result;
    }

    @NonNull
    @Override
    protected String getApiEndpoint() {
        return ENDPOINT;
    }

    @NonNull
    @Override
    protected Iterable<Pair<String, ?>> getQueryParameters() {
        return Arrays.<Pair<String, ?>>asList(
                new Pair<>(ID_PARAMETER, id),
                new Pair<>(PAGE_PARAMETER, page),
                new Pair<>(LIMIT_PARAMETER, limit),
                new Pair<>(SORT_PARAMETER, sortType)
        );
    }
}
