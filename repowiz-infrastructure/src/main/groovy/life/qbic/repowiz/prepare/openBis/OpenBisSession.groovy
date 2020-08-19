package life.qbic.repowiz.prepare.openBis

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.ethz.sis.openbis.generic.dssapi.v3.IDataStoreServerApi
import ch.systemsx.cisd.common.spring.HttpInvokerUtils

/**
 * This class sets up a connection to OpenBis
 *
 * With the provided user credentials and the OpenBis URL this class sets up a OpenBis session through which data can be fetched
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class OpenBisSession {

//todo convert to singelton?? see postman-core-lib
    String sessionToken
    IApplicationServerApi v3
    IDataStoreServerApi dss

    /**
     * Creates a session for a user and a specific OpenBis instance
     * @param user describes the user name in OpenBis
     * @param password is the password for the given user
     * @param url for a specific OpenBis instance
     */
    OpenBisSession(String user, String password, String url) {

        String ass = url + "/openbis/openbis" + IApplicationServerApi.SERVICE_URL
        String ds = url + ":444" + "/datastore_server" + IDataStoreServerApi.SERVICE_URL

        v3 = HttpInvokerUtils.createServiceStub(IApplicationServerApi.class, ass, 10000)

        this.dss = HttpInvokerUtils.createStreamSupportingServiceStub(IDataStoreServerApi.class,
                ds, 10000)

        sessionToken = v3.login(user, password)
    }

    /**
     * Closes the session by logging out the user
     */
    void logout() {
        v3.logout(sessionToken);
    }
}
