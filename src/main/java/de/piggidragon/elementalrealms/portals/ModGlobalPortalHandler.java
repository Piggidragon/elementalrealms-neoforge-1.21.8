package de.piggidragon.elementalrealms.portals;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.variants.PortalVariant;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the global portal network across all dimensions.
 * Handles portal generation, distribution, and location tracking.
 */
public class ModGlobalPortalHandler {

    // EINE GLOBALE MAP PRO DIMENSION - alle Portale zusammen
    public static final Map<ResourceKey<Level>, Set<GlobalPortalCandidates>> portalCandidates = new ConcurrentHashMap<>();

    public static final int PLAYER_PORTAL_RADIUS = 128;      // 128 Chunks = ~2km Radius (4km x 4km Bereich)
    public static final int PORTALS_PER_PLAYER = 15;         // 15 Portale pro Spieler-Bereich
    public static final int CANDIDATE_SPACING = 8;           // 8 Chunks = ~128 Blöcke Mindestabstand

    /**
     * Aktiviert Portal-Generation für alle Dimensionen.
     */
    public static void activateGlobalPortalGeneration(MinecraftServer server) {
        // Für jede Dimension Portal-Candidates generieren
        for (ServerLevel level : server.getAllLevels()) {
            if (isValidDimension(level)) {
                generatePortalCandidatesForDimension(level, server);
            }
        }
    }

    /**
     * Generiert Portal-Candidates für eine komplette Dimension basierend auf allen Spielern.
     */
    private static void generatePortalCandidatesForDimension(ServerLevel level, MinecraftServer server) {
        ResourceKey<Level> dimension = level.dimension();

        // HOLE GLOBALE MAP FÜR DIESE DIMENSION (oder erstelle neue)
        Set<GlobalPortalCandidates> globalPortalSet = portalCandidates.computeIfAbsent(
                dimension,
                k -> ConcurrentHashMap.newKeySet() // Thread-safe Set
        );

        RandomSource random = RandomSource.create(level.getSeed() + dimension.hashCode());
        int totalGenerated = 0;

        // Für jeden Spieler Portal-Candidates in seinem Bereich generieren
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (player.level() == level) { // Nur Spieler in dieser Dimension
                int generated = generatePortalCandidatesAroundPlayer(player, globalPortalSet, random);
                totalGenerated += generated;
            }
        }

        ElementalRealms.LOGGER.info("Generated " + totalGenerated + " portals for dimension " + dimension);
    }

    /**
     * Generiert Portal-Candidates um einen bestimmten Spieler und fügt sie zur GLOBALEN MAP hinzu.
     */
    private static int generatePortalCandidatesAroundPlayer(ServerPlayer player,
                                                            Set<GlobalPortalCandidates> globalPortalSet,
                                                            RandomSource random) {
        ChunkPos playerChunk = new ChunkPos(player.blockPosition());
        int generated = 0;
        int maxAttempts = PORTALS_PER_PLAYER * 5; // Mehr Versuche wegen Collision-Detection

        for (int attempt = 0; attempt < maxAttempts && generated < PORTALS_PER_PLAYER; attempt++) {
            // Zufällige Position um den Spieler (4km x 4km Bereich)
            int offsetX = random.nextInt(-PLAYER_PORTAL_RADIUS, PLAYER_PORTAL_RADIUS);
            int offsetZ = random.nextInt(-PLAYER_PORTAL_RADIUS, PLAYER_PORTAL_RADIUS);

            ChunkPos candidatePos = new ChunkPos(
                    playerChunk.x + offsetX,
                    playerChunk.z + offsetZ
            );

            GlobalPortalCandidates candidate = new GlobalPortalCandidates(
                    candidatePos,
                    determinePortalVariant(player.level().dimension())
            );

            // PRÜFE GEGEN ALLE PORTALE IN DER GLOBALEN MAP
            if (canAddPortalToGlobalSet(candidate, globalPortalSet)) {
                globalPortalSet.add(candidate); // Füge zur globalen Map hinzu
                generated++;
            }
        }

        return generated;
    }

    /**
     * Prüft ob ein Portal zur globalen Map hinzugefügt werden kann.
     * Kombiniert Existenz-Check und Distanz-Check.
     */
    private static boolean canAddPortalToGlobalSet(GlobalPortalCandidates candidate,
                                                   Set<GlobalPortalCandidates> globalPortalSet) {

        // 1. EXISTENZ-CHECK: Bereits in der globalen Map?
        if (globalPortalSet.contains(candidate)) {
            return false; // Bereits vorhanden
        }

        // 2. DISTANZ-CHECK: Zu nah an existierenden Portalen?
        return isMinDistanceRespectedGlobal(candidate, globalPortalSet, CANDIDATE_SPACING);
    }

    /**
     * Prüft Mindestabstand gegen ALLE Portale in der globalen Map.
     */
    private static boolean isMinDistanceRespectedGlobal(GlobalPortalCandidates candidate,
                                                        Set<GlobalPortalCandidates> globalPortalSet,
                                                        int minDistanceChunks) {

        long minDistanceSqr = (long) minDistanceChunks * minDistanceChunks;

        // PRÜFE GEGEN ALLE PORTALE IN DER GLOBALEN MAP
        for (GlobalPortalCandidates existing : globalPortalSet) {
            long distanceSqr = getChunkDistanceSqr(candidate.chunkPos, existing.chunkPos);

            if (distanceSqr < minDistanceSqr) {
                return false; // Zu nah an existierendem Portal
            }
        }
        return true; // Alle Distanzen sind OK
    }

    /**
     * Findet das nächste Portal für Compass-Funktionalität aus der GLOBALEN MAP.
     */
    public static GlobalPortalCandidates findNearestPortal(ServerLevel level, ChunkPos playerChunk) {
        Set<GlobalPortalCandidates> globalPortals = portalCandidates.get(level.dimension());
        if (globalPortals == null || globalPortals.isEmpty()) return null;

        return globalPortals.stream()
                .min((a, b) -> Long.compare(
                        getChunkDistanceSqr(playerChunk, a.chunkPos),
                        getChunkDistanceSqr(playerChunk, b.chunkPos)
                ))
                .orElse(null);
    }

    /**
     * Prüft ob in einem Chunk ein Portal gespawnt werden soll (aus GLOBALER MAP).
     */
    public static GlobalPortalCandidates getPortalForChunk(ServerLevel level, ChunkPos chunkPos) {
        Set<GlobalPortalCandidates> globalPortals = portalCandidates.get(level.dimension());
        if (globalPortals == null) return null;

        return globalPortals.stream()
                .filter(candidate -> candidate.chunkPos.equals(chunkPos))
                .filter(candidate -> !candidate.generated)
                .findFirst()
                .orElse(null);
    }

    /**
     * Markiert Portal als generiert in der GLOBALEN MAP.
     */
    public static void markPortalGenerated(GlobalPortalCandidates candidate) {
        candidate.generated = true;
    }

    /**
     * Gibt die Anzahl aller Portale in einer Dimension zurück.
     */
    public static int getPortalCountForDimension(ResourceKey<Level> dimension) {
        Set<GlobalPortalCandidates> portals = portalCandidates.get(dimension);
        return portals != null ? portals.size() : 0;
    }

    /**
     * Gibt alle Portale in einem Radius um eine Position zurück.
     */
    public static Set<GlobalPortalCandidates> getPortalsInRadius(ServerLevel level, ChunkPos center, int radiusChunks) {
        Set<GlobalPortalCandidates> globalPortals = portalCandidates.get(level.dimension());
        if (globalPortals == null) return Set.of();

        long radiusSqr = (long) radiusChunks * radiusChunks;

        return globalPortals.stream()
                .filter(portal -> getChunkDistanceSqr(center, portal.chunkPos) <= radiusSqr)
                .collect(java.util.stream.Collectors.toSet());
    }

    // Helper methods
    private static long getChunkDistanceSqr(ChunkPos a, ChunkPos b) {
        long deltaX = a.x - b.x;
        long deltaZ = a.z - b.z;
        return deltaX * deltaX + deltaZ * deltaZ;
    }

    private static PortalVariant determinePortalVariant(ResourceKey<Level> dimension) {
        if (dimension == Level.OVERWORLD) return PortalVariant.ELEMENTAL;
        if (dimension == Level.NETHER) return PortalVariant.DEVIANT;
        if (dimension == Level.END) return PortalVariant.ETERNAL;
        return PortalVariant.ELEMENTAL; // Default
    }

    private static boolean isValidDimension(ServerLevel level) {
        return level.dimension() == Level.OVERWORLD ||
                level.dimension() == Level.NETHER ||
                level.dimension() == Level.END;
    }

    /**
     * Debug-Information über Portal-Netzwerk.
     */
    public static void printPortalStats() {
        System.out.println("=== Global Portal Network Stats ===");
        for (Map.Entry<ResourceKey<Level>, Set<GlobalPortalCandidates>> entry : portalCandidates.entrySet()) {
            ResourceKey<Level> dim = entry.getKey();
            Set<GlobalPortalCandidates> portals = entry.getValue();

            long generated = portals.stream().mapToLong(p -> p.generated ? 1 : 0).sum();
            System.out.printf("%s: %d total candidates, %d generated portals%n",
                    dim.location().getPath(), portals.size(), generated);
        }
        System.out.println("===================================");
    }

    public static class GlobalPortalCandidates {
        public final ChunkPos chunkPos;
        public volatile boolean generated; // Thread-safe für Multiplayer
        public final PortalVariant variant;

        public GlobalPortalCandidates(ChunkPos chunkPos, PortalVariant variant) {
            this.chunkPos = chunkPos;
            this.generated = false;
            this.variant = variant;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof GlobalPortalCandidates)) return false;
            GlobalPortalCandidates other = (GlobalPortalCandidates) obj;
            return chunkPos.equals(other.chunkPos);
        }

        @Override
        public int hashCode() {
            return chunkPos.hashCode();
        }

        @Override
        public String toString() {
            return "Portal@" + chunkPos + " (generated=" + generated + ", variant=" + variant + ")";
        }
    }
}
