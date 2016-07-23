package com.proxerme.library.connection.experimental.chat.request;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.afollestad.bridge.Response;
import com.proxerme.library.connection.ProxerRequest;
import com.proxerme.library.connection.experimental.chat.result.MessagesResult;
import com.proxerme.library.info.ProxerTag;
import com.proxerme.library.info.ProxerUrlHolder;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 */

public class MessagesRequest extends ProxerRequest<MessagesResult> {

    private static final String MESSAGES_URL = "/messages?format=json&json=messages&id=%s&p=%s";

    private String conferenceId;
    private int page;

    public MessagesRequest(@NonNull String conferenceId, @IntRange(from = 0) int page) {
        this.conferenceId = conferenceId;
        this.page = page;
    }

    @Override
    protected MessagesResult parse(Response response) throws Exception {
        return response.asClass(MessagesResult.class);
    }

    @Override
    protected int getTag() {
        return ProxerTag.MESSAGES;
    }

    @NonNull
    @Override
    protected String getURL() {
        return ProxerUrlHolder.getHost() + MESSAGES_URL;
    }

    @Nullable
    @Override
    protected String[] getParameters() {
        return new String[]{conferenceId, String.valueOf(page)};
    }
}