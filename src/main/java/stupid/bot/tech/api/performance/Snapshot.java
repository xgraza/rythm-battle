package stupid.bot.tech.api.performance;

public record Snapshot(long timeMS,
                       int memoryUsedMS,
                       int threadCounts) {
}
