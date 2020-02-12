package life.qbic.repowiz

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.systemsx.cisd.common.spring.HttpInvokerUtils

class OpenBisSession {

    String user
    String password
    String as_url
    String sessionToken
    IApplicationServerApi v3

    OpenBisSession(String user, String password, as_url){
        this.user = user
        this.password = password
        this.as_url = as_url + IApplicationServerApi.SERVICE_URL

        v3 = HttpInvokerUtils.createServiceStub(IApplicationServerApi.class, this.as_url, 10000)
        sessionToken = v3.login(user, password)
    }

    def logout(){
        v3.logout(sessionToken);
    }
}
