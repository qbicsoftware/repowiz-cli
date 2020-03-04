package life.qbic.repowiz.prepare.projectSearch.openBis

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.ethz.sis.openbis.generic.dssapi.v3.IDataStoreServerApi
import ch.systemsx.cisd.common.spring.HttpInvokerUtils

class OpenBisSession {

//todo convert to singelton?? see postman-core-lib
    String sessionToken
    IApplicationServerApi v3
    IDataStoreServerApi dss

    OpenBisSession(String user, String password, url){

        String ass = url + "/openbis/openbis" + IApplicationServerApi.SERVICE_URL
        String ds = url + ":444" + "/datastore_server" + IDataStoreServerApi.SERVICE_URL

        v3 = HttpInvokerUtils.createServiceStub(IApplicationServerApi.class, ass, 10000)

        this.dss = HttpInvokerUtils.createStreamSupportingServiceStub(IDataStoreServerApi.class,
                       ds, 10000)

        sessionToken = v3.login(user, password)
    }

    def logout(){
        v3.logout(sessionToken);
    }
}
