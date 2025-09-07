package stupid.bot.tech.impl.event;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import stupid.bot.tech.api.logger.Logger;

/**
 * @author xgraza
 * @since 0.0.1-beta
 */
public final class ReadyEventListener extends ListenerAdapter
{
    @Override
    public void onReady(@NotNull final ReadyEvent event)
    {
        final JDA jda = event.getJDA();
        Logger.info("Logged in as {} ({})",
                jda.getSelfUser().getName(),
                jda.getSelfUser().getId());

        jda.getRestPing().queue((latency) ->
        {
            Logger.info("===[ Startup Statistics ]===");
            Logger.info("   REST Ping: {}ms", latency);
            Logger.info("Gateway Ping: {}ms", jda.getGatewayPing());
            Logger.info("      Guilds: {}", event.getGuildTotalCount());
            Logger.info("   Available: {}", event.getGuildAvailableCount());
            Logger.info(" Unavailable: {}", event.getGuildUnavailableCount());
        });
    }
}
