package stupid.bot.tech.util.lang;

import java.util.Collections;
import java.util.LinkedList;

/**
 * @author xgraza
 * @since 0.0.1-beta
 */
public final class CollectionsUtil
{
    public static <T> LinkedList<T> linkedList(final T... obj)
    {
        final LinkedList<T> list = new LinkedList<>();
        Collections.addAll(list, obj);
        return list;
    }
}
