package stupid.bot.tech.util.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author xgraza
 * @since 0.0.1-beta
 */
public final class FileUtil
{
    public static final File ROOT = Paths.get("").toFile();

    public static InputStream createStream(final File file)
    {
        try
        {
            return Files.newInputStream(file.toPath());
        } catch (IOException e)
        {
            return null;
        }
    }

    public static File resolve(final File file, final String... path)
    {
        Path p = file.toPath();
        for (final String item : path)
        {
            p = p.resolve(item);
        }
        return p.toFile();
    }

    public static File resolve(final String... path)
    {
        return resolve(ROOT, path);
    }

    public static String readFile(final File file) throws IOException
    {
        final StringBuilder builder = new StringBuilder();
        try (final InputStream stream = Files.newInputStream(file.toPath()))
        {
            int b;
            while ((b = stream.read()) != -1)
            {
                builder.append((char)b);
            }
        }
        return builder.toString();
    }
}
