package org.noobs2d.grf.system;

public enum Environment {

    LOCAL,

    DEV,

    TESTING,

    PRODUCTION;

    public String toDirectoryName() {
        return this.name().replaceAll("_", "-").toLowerCase();
    }

}
