package stupid.bot.tech;

import stupid.bot.tech.util.resource.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author xgraza
 * @since 09/04/25
 */
public final class Config
{
    private static final Properties PROPS;

    static
    {
        PROPS = new Properties();
        try
        {
            final InputStream stream = FileUtil.createStream(FileUtil.resolve("bot.properties"));
            PROPS.load(stream);
            assert stream != null;
            stream.close();
        } catch (IOException | NullPointerException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static boolean isPresent(final String name)
    {
        return PROPS.containsKey(name);
    }

    public static String get(final String name)
    {
        return PROPS.getProperty(name);
    }

    public static void set(final String name, final String value)
    {
        PROPS.setProperty(name, value);
    }
}
