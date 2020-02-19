package life.qbic.repowiz.prepare

import life.qbic.repowiz.io.JsonParser
import life.qbic.repowiz.prepare.projectSearch.OpenBisSession

class OpenBisSessionSpecification {
    OpenBisSession session

    def setup(){
        JsonParser props = new JsonParser()
        Map cred = (Map) props.parseAsFile("credentials.json.properties")
        session = new OpenBisSession((String) cred.get("user"), (String) cred.get("password"), (String) cred.get("as_url"))
    }

    def "ddddd"(){
        //is connection build?
        //gets right projects?
    }




}
