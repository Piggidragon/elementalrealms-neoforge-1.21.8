package de.piggidragon.elementalrealms.level;

public enum ModLevelEnum {
    SCHOOL_DIMENSION("school");


    private final String path;

    ModLevelEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
