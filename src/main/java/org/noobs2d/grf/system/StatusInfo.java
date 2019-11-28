package org.noobs2d.grf.system;

public class StatusInfo {

    public final static String STATUS_MAINTENANCE = "status_maintenance";
    public final static String STATUS_HEALTHY = "status_healthy";

    private String messageZh;

    public StatusInfo(String status) {
        this.status = status;
    }

    private String messageVi;

    private String status;
    private String messageEn;

    public StatusInfo() {
    }

    public StatusInfo(String status, String messageZh, String messageVi, String messageEn) {
        this(status);
        this.messageEn = messageEn;
        this.messageZh = messageZh;
        this.messageVi = messageVi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage(Language language) {
        if (language.isChinese()) {
            return messageZh;
        }

        if (language.isVietnamese()) {
            return messageVi;
        }

        return messageEn;
    }

    public boolean isMaintenance() {
        return STATUS_MAINTENANCE.equalsIgnoreCase(this.status);
    }
}
