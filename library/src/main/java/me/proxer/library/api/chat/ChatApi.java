package me.proxer.library.api.chat;

import retrofit2.Retrofit;

/**
 * Api for the Chat class.
 *
 * @author Ruben Gees
 */
public final class ChatApi {

    private final InternalApi internalApi;

    /**
     * Only for internal use.
     */
    public ChatApi(final Retrofit retrofit) {
        this.internalApi = retrofit.create(InternalApi.class);
    }

    /**
     * Returns the respective endpoint.
     */
    public PublicChatRoomsEndpoint publicRooms() {
        return new PublicChatRoomsEndpoint(internalApi);
    }

    /**
     * Returns the respective endpoint.
     */
    public UserChatRoomsEndpoint userRooms() {
        return new UserChatRoomsEndpoint(internalApi);
    }

    /**
     * Returns the respective endpoint.
     */
    public ChatMessagesEndpoint messages(final String roomId) {
        return new ChatMessagesEndpoint(internalApi, roomId);
    }

    /**
     * Returns the respective endpoint.
     */
    public SendChatMessageEndpoint sendMessage(final String roomId, final String message) {
        return new SendChatMessageEndpoint(internalApi, roomId, message);
    }
}
