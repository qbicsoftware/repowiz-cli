package life.qbic.repowiz.prepare


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
        session = new OpenBisSession((String) cred.get("user"), (String) cred.get("password"), (String) cred.get("server_url"))
        //todo need dss to download data! --> add to openbis session
    }

    def "experiment"(){
        given:
        ProjectSearchMapper mapper = new ProjectSearchMapper(session.v3,session.dss, session.sessionToken)
        mapper.addProjectSearchOutput(out)

        when:
        mapper.loadProjectInformation("QFSVI")
        mapper.loadOpenBisExperimentInfo()

        then:
        true //13 experiments
    }

    def "sample"(){
        given:
        ProjectSearchMapper mapper = new ProjectSearchMapper(session.v3,session.dss,session.sessionToken)
        mapper.addProjectSearchOutput(out)

        println session.dss

        when:
        mapper.loadProjectInformation("QFSVI")
        mapper.loadOpenBisSampleInfo()

        then:
        true //13 experiments
    }

}
