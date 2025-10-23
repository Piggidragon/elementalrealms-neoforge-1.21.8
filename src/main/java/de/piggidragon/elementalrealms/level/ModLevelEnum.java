package de.piggidragon.elementalrealms.level;

/**
 * Enumeration of custom dimension paths for easier reference and iteration.
 * Provides a centralized location for managing dimension identifiers.
 *
 * <p>Currently not actively used but available for future dimension management features
 * such as dimension lists, teleportation menus, or configuration systems.</p>
 */
public enum ModLevelEnum {
    /**
     * School dimension path identifier
     */
    SCHOOL_DIMENSION("school");

    /**
     * The dimension's path component in its ResourceLocation
     */
    private final String path;

    /**
     * Constructs a dimension enum entry with its path identifier.
     *
     * @param path The path component for this dimension's ResourceLocation
     */
    ModLevelEnum(String path) {
        this.path = path;
    }

    /**
     * Gets the path identifier for this dimension.
     *
     * @return The dimension's path string (e.g., "school")
     */
    public String getPath() {
        return path;
    }
}
