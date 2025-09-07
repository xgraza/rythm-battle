package stupid.bot.tech.util.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author xgraza
 * @since 0.0.1-beta
 */
public final class ResourceUtil
{
    public static InputStream getResource(final String... path)
    {
        return ResourceUtil.class.getResourceAsStream("/" + String.join("/", path));
    }

    public static void writeStream(final OutputStream stream, final String body) throws IOException
    {
        if (stream == null)
        {
            return;
        }
        final byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        stream.write(bytes, 0, bytes.length);
    }

    public static String readStream(final InputStream stream) throws IOException
    {
        if (stream == null)
        {
            return null;
        }
        final StringBuilder builder = new StringBuilder();
        int b;
        while ((b = stream.read()) != -1)
        {
            builder.append((char)b);
        }
        stream.close();
        return builder.toString();
    }
}
