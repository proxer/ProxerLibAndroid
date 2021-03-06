@file:JvmName("ProxerUrls")

package me.proxer.library.util

import me.proxer.library.entity.notifications.NewsArticle
import me.proxer.library.enums.AnimeLanguage
import me.proxer.library.enums.Device
import me.proxer.library.enums.Language
import okhttp3.HttpUrl

/**
 * Utility class with various static methods for retrieving web and image links.
 * Moreover it holds the base urls of web, api and image cdn.
 *
 * @author Ruben Gees
 */
object ProxerUrls {

    private val PROXER_STREAM_FILE_HOST_PATTERN = Regex("s[0-9]+(?:(?:.|-)psc?|\\.stream)\\.proxer\\.me")
    private val PROXER_MANGA_HOST_PATTERN = Regex("manga[0-9]+\\.proxer\\.me")

    /**
     * Returns the base url for all web pages.
     */
    val webBase: HttpUrl = HttpUrl.Builder()
        .scheme("https")
        .host("proxer.me")
        .addPathSegment("")
        .build()

    /**
     * Returns the base url for all api calls.
     */
    val apiBase: HttpUrl = webBase.newBuilder()
        .addPathSegment("api")
        .addPathSegment("v1")
        .addPathSegment("")
        .build()

    /**
     * Returns the base url for all image links.
     */
    val cdnBase: HttpUrl = HttpUrl.Builder()
        .scheme("https")
        .host("cdn.proxer.me")
        .addPathSegment("")
        .build()

    /**
     * Returns the base url for all stream links.
     */
    val streamBase: HttpUrl = HttpUrl.Builder()
        .scheme("https")
        .host("stream.proxer.me")
        .addPathSegment("")
        .build()

    /**
     * Returns the base url for all proxy links.
     */
    val proxyBase: HttpUrl = HttpUrl.Builder()
        .scheme("https")
        .host("proxy.proxer.me")
        .build()

    /**
     * Returns the image link of a [NewsArticle], based on its [id] and [image].
     */
    fun newsImage(id: String, image: String): HttpUrl {
        return cdnBase.newBuilder()
            .addPathSegment("news")
            .addPathSegment("tmp")
            .addPathSegment("${id}_$image.png")
            .build()
    }

    /**
     * Returns the image link of the user.
     */
    fun userImage(image: String): HttpUrl {
        return cdnBase.newBuilder()
            .addPathSegment("avatar")
            .addPathSegment(image)
            .build()
    }

    /**
     * Returns the cover image link of the entry.
     */
    fun entryImage(id: String): HttpUrl {
        return cdnBase.newBuilder()
            .addPathSegment("cover")
            .addPathSegment("tmp")
            .addPathSegment("$id.jpg")
            .build()
    }

    /**
     * Returns the link to the image behind the proxy.
     */
    fun proxyImage(url: String): HttpUrl {
        return proxyBase.newBuilder()
            .addPathSegment("index.php")
            .addQueryParameter("url", url)
            .build()
    }

    /**
     * Returns the link to the image behind the proxy.
     */
    fun proxyImage(url: HttpUrl): HttpUrl {
        return proxyImage(url.toString())
    }

    /**
     * Returns the image link of the translator group.
     */
    fun translatorGroupImage(id: String): HttpUrl {
        return cdnBase.newBuilder()
            .addPathSegment("translatorgroups")
            .addPathSegment("$id.jpg")
            .build()
    }

    /**
     * Returns the image link of the industry.
     */
    fun industryImage(id: String): HttpUrl {
        return cdnBase.newBuilder()
            .addPathSegment("industry")
            .addPathSegment("$id.jpg")
            .build()
    }

    /**
     * Returns the image link of the hoster.
     */
    fun hosterImage(id: String): HttpUrl {
        return webBase.newBuilder()
            .addPathSegment("images")
            .addPathSegment("hoster")
            .addPathSegment(id)
            .build()
    }

    /**
     * Returns the image link of the page, based on the [server] number, the [entryId], the [id] of
     * the chapter and the file's [name] of the page.
     */
    fun mangaPageImage(server: String, entryId: String, id: String, name: String): HttpUrl {
        return HttpUrl.Builder()
            .scheme("https")
            .host("manga$server.proxer.me")
            .addPathSegment("f")
            .addPathSegment(entryId)
            .addPathSegment(id)
            .addPathSegment(name)
            .build()
    }

    /**
     * Returns the link for the donation web page.
     */
    @JvmOverloads
    fun donateWeb(device: Device = Device.DEFAULT): HttpUrl {
        return webBase.newBuilder()
            .addPathSegment("donate")
            .addQueryParameter("device", ProxerUtils.getSafeApiEnumName(device))
            .build()
    }

    /**
     * Returns the link for the wiki web page, based on the [topic].
     */
    fun wikiWeb(topic: String): HttpUrl {
        return webBase.newBuilder()
            .addPathSegment("wiki")
            .addPathSegment(topic)
            .addQueryParameter("device", ProxerUtils.getSafeApiEnumName(Device.DEFAULT))
            .build()
    }

    /**
     * Returns the link for the user's web page.
     */
    @JvmOverloads
    fun userWeb(id: String, device: Device = Device.DEFAULT): HttpUrl {
        return webBase.newBuilder()
            .addPathSegment("user")
            .addPathSegment(id)
            .addQueryParameter("device", ProxerUtils.getSafeApiEnumName(device))
            .build()
    }

    /**
     * Returns the link for the web page of the requested forum thread, residing in the category, specified by the
     * [categoryId].
     */
    @JvmOverloads
    fun forumWeb(categoryId: String, threadId: String, device: Device = Device.DEFAULT): HttpUrl {
        return webBase.newBuilder()
            .addPathSegment("forum")
            .addPathSegment(categoryId)
            .addPathSegment(threadId)
            .addQueryParameter("device", ProxerUtils.getSafeApiEnumName(device))
            .build()
    }

    /**
     * Returns the link for the web page of the requested news article, residing in the category, specified by the
     * [categoryId].
     */
    @JvmOverloads
    fun newsWeb(categoryId: String, threadId: String, device: Device = Device.DEFAULT): HttpUrl {
        return forumWeb(categoryId, threadId, device)
    }

    /**
     * Returns the link for the web page of the requested info page.
     */
    @JvmOverloads
    fun infoWeb(id: String, device: Device = Device.DEFAULT): HttpUrl {
        return webBase.newBuilder()
            .addPathSegment("info")
            .addPathSegment(id)
            .addQueryParameter("device", ProxerUtils.getSafeApiEnumName(device))
            .build()
    }

    /**
     * Returns the link for the web page of the requested industry.
     */
    fun industryWeb(id: String): HttpUrl {
        return webBase.newBuilder()
            .addPathSegment("industry")
            .addPathSegment(id)
            .addQueryParameter("device", ProxerUtils.getSafeApiEnumName(Device.DEFAULT))
            .build()
    }

    /**
     * Returns the link for the web page of the requested translator group.
     */
    fun translatorGroupWeb(id: String): HttpUrl {
        return webBase.newBuilder()
            .addPathSegment("translatorgroups")
            .addPathSegment(id)
            .addQueryParameter("device", ProxerUtils.getSafeApiEnumName(Device.DEFAULT))
            .build()
    }

    /**
     * Returns the link for the watch page of an anime.
     */
    @JvmOverloads
    fun animeWeb(id: String, episode: Int, language: AnimeLanguage, device: Device = Device.DEFAULT): HttpUrl {
        return webBase.newBuilder()
            .addPathSegment("watch")
            .addPathSegment(id)
            .addPathSegment(episode.toString())
            .addPathSegment(ProxerUtils.getSafeApiEnumName(language))
            .addQueryParameter("device", ProxerUtils.getSafeApiEnumName(device))
            .build()
    }

    /**
     * Returns the link for the read page of a manga.
     */
    @JvmOverloads
    fun mangaWeb(id: String, episode: Int, language: Language, device: Device = Device.DEFAULT): HttpUrl {
        return webBase.newBuilder()
            .addPathSegment("chapter")
            .addPathSegment(id)
            .addPathSegment(episode.toString())
            .addPathSegment(ProxerUtils.getSafeApiEnumName(language))
            .addQueryParameter("device", ProxerUtils.getSafeApiEnumName(device))
            .build()
    }

    /**
     * Returns the link for the web page to solve the captcha.
     *
     * Optionally with the [ip] parameter to give a different from the one in the network request.
     */
    @JvmOverloads
    fun captchaWeb(ip: String? = null, device: Device = Device.DEFAULT): HttpUrl {
        return webBase.newBuilder()
            .addPathSegment("misc")
            .addPathSegment("captcha")
            .apply { if (!ip.isNullOrBlank()) addQueryParameter("ip", ip) }
            .addQueryParameter("device", ProxerUtils.getSafeApiEnumName(device))
            .build()
    }

    /**
     * Returns the link for the web page to register.
     */
    fun registerWeb(): HttpUrl {
        return webBase.newBuilder()
            .addPathSegment("register")
            .addQueryParameter("device", ProxerUtils.getSafeApiEnumName(Device.DEFAULT))
            .build()
    }

    /**
     * Returns if this url has a valid host of proxer.
     */
    @JvmStatic
    val HttpUrl.hasProxerHost: Boolean
        @JvmName("hasProxerHost")
        get() = this.hasProxerWebOrCdnOrStreamHost || this.hasProxerStreamFileHost || this.hasProxerMangaHost ||
            this.hasProxerProxyHost

    /**
     * Returns if the passed url has a valid host of proxer.
     */
    @JvmName("-deprecated_hasProxerHost")
    @Deprecated(
        message = "moved to extension function",
        replaceWith = ReplaceWith(
            expression = "url.hasProxerHost",
            imports = ["me.proxer.library.util.ProxerUrls.hasProxerHost"]
        ),
        level = DeprecationLevel.ERROR
    )
    fun hasProxerHost(url: HttpUrl) = url.hasProxerHost

    /**
     * Returns if this url has a valid host of proxer or the proxer cdn.
     */
    @JvmStatic
    val HttpUrl.hasProxerWebOrCdnOrStreamHost
        @JvmName("hasProxerWebOrCdnOrStreamHost")
        get() = host == webBase.host || host == cdnBase.host || host == streamBase.host

    /**
     * Returns if the passed url has a valid host of proxer or the proxer cdn.
     */
    @JvmName("-deprecated_hasProxerWebOrCdnOrStreamHost")
    @Deprecated(
        message = "moved to extension function",
        replaceWith = ReplaceWith(
            expression = "url.hasProxerWebOrCdnOrStreamHost",
            imports = ["me.proxer.library.util.ProxerUrls.hasProxerWebOrCdnOrStreamHost"]
        ),
        level = DeprecationLevel.ERROR
    )
    fun hasProxerWebOrCdnOrStreamHost(url: HttpUrl) = url.hasProxerWebOrCdnOrStreamHost

    /**
     * Returns if this url has a valid proxer stream host.
     */
    @JvmStatic
    val HttpUrl.hasProxerStreamFileHost
        @JvmName("hasProxerStreamFileHost")
        get() = PROXER_STREAM_FILE_HOST_PATTERN.matches(host)

    /**
     * Returns if the passed url has a valid proxer stream host.
     */
    @JvmName("-deprecated_hasProxerStreamFileHost")
    @Deprecated(
        message = "moved to extension function",
        replaceWith = ReplaceWith(
            expression = "url.hasProxerStreamFileHost",
            imports = ["me.proxer.library.util.ProxerUrls.hasProxerStreamFileHost"]
        ),
        level = DeprecationLevel.ERROR
    )
    fun hasProxerStreamFileHost(url: HttpUrl) = url.hasProxerStreamFileHost

    /**
     * Returns if this url has a valid proxer manga host.
     */
    @JvmStatic
    val HttpUrl.hasProxerMangaHost
        @JvmName("hasProxerMangaHost")
        get() = PROXER_MANGA_HOST_PATTERN.matches(host)

    /**
     * Returns if the passed url has a valid proxer manga host.
     */
    @JvmName("-deprecated_hasProxerMangaHost")
    @Deprecated(
        message = "moved to extension function",
        replaceWith = ReplaceWith(
            expression = "url.hasProxerMangaHost",
            imports = ["me.proxer.library.util.ProxerUrls.hasProxerMangaHost"]
        ),
        level = DeprecationLevel.ERROR
    )
    fun hasProxerMangaHost(url: HttpUrl) = url.hasProxerMangaHost

    /**
     * Returns if this url has a valid proxer image proxy host.
     */
    @JvmStatic
    val HttpUrl.hasProxerProxyHost
        @JvmName("hasProxerProxyHost")
        get() = host == proxyBase.host

    /**
     * Returns if the passed url has a valid proxer image proxy host.
     */
    @JvmName("-deprecated_hasProxerProxyHost")
    @Deprecated(
        message = "moved to extension function",
        replaceWith = ReplaceWith(
            expression = "url.hasProxerProxyHost",
            imports = ["me.proxer.library.util.ProxerUrls.hasProxerProxyHost"]
        ),
        level = DeprecationLevel.ERROR
    )
    fun hasProxerProxyHost(url: HttpUrl) = url.hasProxerProxyHost
}
