package life.qbic.repowiz.prepare

import life.qbic.repowiz.cli.SubmissionPresenter
import life.qbic.repowiz.io.JsonParser
import life.qbic.repowiz.prepare.projectSearch.OpenBisSession
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchMapper
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchOutput
import spock.lang.Specification

class OpenBisSessionSpecification extends Specification{

    OpenBisSession session
    ProjectSearchOutput out = Mock(ProjectSearchOutput)


    def setup(){
        JsonParser props = new JsonParser()
        Map cred = (Map) props.parseAsFile("credentials.json.properties")
        session = new OpenBisSession((String) cred.get("user"), (String) cred.get("password"), (String) cred.get("as_url"))
        //todo need dss to download data! --> add to openbis session
    }

    def "ddddd"(){
        given:
        ProjectSearchMapper mapper = new ProjectSearchMapper(session.v3,session.sessionToken)
        mapper.addProjectSearchOutput(out)

        when:
        def res = mapper.loadProject("QFSVI")
        mapper.loadSampleInfo()

        then:
        res.size() == 13 //13 experiments
    }




}
