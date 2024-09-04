package sh.damon.stackmob.util;

public class CommandUtil {
    public interface Callback<T> {
        T call();
    }

    public static <T> T tryGet(CommandUtil.Callback<T> callback, CommandUtil.Callback<T> fallback) {
        try {
            return callback.call();
        }
        catch (Exception e) {
            return fallback.call();
        }
    }
}
