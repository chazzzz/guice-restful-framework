package org.noobs2d.grf.web.security.session;

public interface SessionsManager {

    void save(UserSession userSession);

    UserSession find(String userId);

    long countAllActive();

    boolean hasExpired(UserSession userSession);

    void refresh(UserSession userSession);
}
