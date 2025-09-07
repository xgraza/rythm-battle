package stupid.bot.tech.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import stupid.bot.tech.Config;
import stupid.bot.tech.api.logger.Logger;
import stupid.bot.tech.util.resource.FileUtil;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @author xgraza
 * @since 0.0.1-beta
 */
public final class WebServer
{
    private static final InetSocketAddress ADDRESS = new InetSocketAddress(
            Config.get("server.host"), Integer.parseInt(Config.get("server.port")));

    private static final File WEB_PUBLIC = FileUtil.resolve("web-src", "public");
    private static final File WEB_DEFAULT = FileUtil.resolve("web-src", "default");
    private static final File VIEW_UNKNOWN_RESOURCE = FileUtil.resolve(WEB_DEFAULT, "unknown_resource.html");

    private final HttpServer httpServer;

    public WebServer() throws IOException
    {
        httpServer = HttpServer.create(ADDRESS, 0);
        httpServer.createContext("/", this::handle);
    }

    public void start()
    {
        Logger.info("Starting HTTP server at {}", ADDRESS);
        httpServer.start();
    }

    private void handle(final HttpExchange ctx) throws IOException
    {
        if (!ctx.getRequestMethod().equalsIgnoreCase("get"))
        {
            writeResponse(ctx, "400", 400);
            return;
        }

        final String path = ctx.getRequestURI().getPath();
        if (path.equals("/") || path.equalsIgnoreCase("/main"))
        {
            final String html = FileUtil.readFile(FileUtil.resolve(
                    "web-src", "public", "main.html"));
            writeResponse(ctx, html, 200);
        } else
        {
            final String[] splitPaths = path.split("/");
            if (!splitPaths[splitPaths.length - 1].endsWith(".html"))
            {
                splitPaths[splitPaths.length - 1] += ".html";
            }

            final File file = FileUtil.resolve(WEB_PUBLIC, splitPaths);
            if (!file.exists())
            {
                String content = FileUtil.readFile(VIEW_UNKNOWN_RESOURCE);
                content = String.format(content, path);
                writeResponse(ctx, content, 400);
                return;
            }

            writeResponse(ctx, FileUtil.readFile(file), 200);
        }
    }

    private void writeResponse(final HttpExchange ctx, final String body, final int code) throws IOException
    {
        final byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ctx.sendResponseHeaders(code, bytes.length);
        ctx.getResponseBody().write(bytes, 0, bytes.length);
        ctx.getResponseBody().close();
    }
}
