package com.dsi.authentication.dao.impl;

import com.dsi.authentication.dao.UserSessionDao;
import com.dsi.authentication.model.UserSession;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by sabbir on 6/15/16.
 */
public class UserSessionDaoImpl extends BaseDao implements UserSessionDao {

    private static final Logger logger = Logger.getLogger(UserSessionDaoImpl.class);

    @Override
    public boolean saveUserSession(UserSession userSession) {
        return save(userSession);
    }

    @Override
    public boolean updateUserSession(UserSession userSession) {
        return update(userSession);
    }

    @Override
    public boolean deleteUserSession(UserSession userSession) {
        return delete(userSession);
    }

    @Override
    public UserSession getUserSessionByUserIdAndAccessToken(String userID, String accessToken) {
        Session session = null;
        UserSession userSession = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM UserSession us WHERE us.userId =:userID AND us.accessToken =:accessToken");
            query.setParameter("userID", userID);
            query.setParameter("accessToken", accessToken);

            userSession = (UserSession) query.uniqueResult();

        }catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());

        } finally {
            if(session != null) {
                close(session);
            }
        }
        return userSession;
    }
}
