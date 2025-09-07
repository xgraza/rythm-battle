package stupid.bot.tech;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.internal.utils.JDALogger;
import stupid.bot.tech.api.logger.Logger;
import stupid.bot.tech.impl.event.ReadyEventListener;
import stupid.bot.tech.util.lang.CollectionsUtil;
import stupid.bot.tech.util.resource.ResourceUtil;
import stupid.bot.tech.web.WebServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * @author xgraza
 * @since 0.0.1-beta
 */
public enum Bot
{
    INSTANCE;

    public static final WebServer SERVER;
    public static JDA JDA;

    static
    {
        try
        {
            SERVER = new WebServer();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    void init() throws InterruptedException
    {
        printArt();
        printDebug();

        JDALogger.setFallbackLoggerEnabled(false);

        SERVER.start();

        Logger.info("Creating JDA instance");
        JDA = createJDAInstance();
        Logger.info("Established Gateway connection, awaiting ready...");
        JDA.awaitReady();
    }

    private JDA createJDAInstance()
    {
        return JDABuilder.createDefault(Config.get("bot.token"))
                .disableCache(CacheFlag.ACTIVITY,
                        CacheFlag.ONLINE_STATUS,
                        CacheFlag.STICKER,
                        CacheFlag.EMOJI,
                        CacheFlag.ROLE_TAGS)
                .setEnabledIntents(GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.SCHEDULED_EVENTS)
                .setCompression(Compression.ZLIB)
                .setActivity(Activity.customStatus("starting up..."))
                .addEventListeners(new ReadyEventListener())
                .build();
    }

    private void printArt()
    {
        if (!Config.isPresent("bot.startup.ascii_art"))
        {
            return;
        }
        try
        {
            final InputStream stream = ResourceUtil.getResource("assets", "startup.txt");
            final String content = ResourceUtil.readStream(stream);
            Logger.success("\n\n" + content);
        } catch (IOException e)
        {
            Logger.error("No ascii art :(");
        }
    }

    private void printDebug()
    {
        if (!Config.isPresent("bot.startup.debug"))
        {
            return;
        }
        Logger.info("Starting {}", BuildConfig.GROUP);
        Logger.info("   Version: {}", BuildConfig.VERSION);
        Logger.info("   Build #: {}", BuildConfig.BUILD);
        Logger.info("   Env    : {}", BuildConfig.ENVIRONMENT);
        Logger.info("   Hash   : {}", BuildConfig.HASH);
        Logger.info("   Branch : {}", BuildConfig.BRANCH);
        Logger.info("   Built @: {}", BuildConfig.BUILD_TIME);
    }
}
