package me.proxer.library.entity.user;

import com.squareup.moshi.Json;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import me.proxer.library.entity.ProxerIdItem;
import me.proxer.library.entity.ProxerImageItem;

import javax.annotation.Nullable;

/**
 * Entity holding information about the user after a login.
 *
 * @author Ruben Gees
 */
@Value
@EqualsAndHashCode(onParam = @__({@Nullable}))
public final class User implements ProxerIdItem, ProxerImageItem {

    /**
     * Returns the id.
     */
    @Getter(onMethod = @__({@Override}))
    @Json(name = "uid")
    private String id;

    /**
     * Returns the image id.
     */
    @Getter(onMethod = @__({@Override}))
    @Json(name = "avatar")
    private String image;

    /**
     * Returns the login token for further authentication.
     */
    @Json(name = "token")
    private String loginToken;
}