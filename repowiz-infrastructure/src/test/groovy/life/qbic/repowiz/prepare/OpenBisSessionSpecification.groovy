package life.qbic.repowiz.prepare


import spock.lang.Specification

class OpenBisSessionSpecification extends Specification {

    /*OpenBisSession session
    ProjectSearchOutput out = Mock(ProjectSearchOutput)

   def setup(){
        JsonParser props = new JsonParser()
        Map cred = (Map) props.parseAsStream("credentials.json.properties")
        session = new OpenBisSession((String) cred.get("user"), (String) cred.get("password"), (String) cred.get("server_url"))
    }

    def "experiment"(){
        given:
        ProjectSearcher mapper = new ProjectSearcher(session.v3,session.dss, session.sessionToken)
        mapper.addProjectSearchOutput(out)

        when:
        mapper.loadProjectInformation("QFSVI")

        then:
        true //13 experiments
    }

    def "sample"(){
        given:
        ProjectSearcher mapper = new ProjectSearcher(session.v3,session.dss,session.sessionToken)
        mapper.addProjectSearchOutput(out)

        when:
        mapper.loadProjectInformation("QFSVI")
        def res = mapper.loadOpenBisDataSetInfo("NGS01QFSVI009AM","fastq")

        then:
        res.get(res.keySet()[0]).sort() == ["I16R019a02_01_S3_L001_R1_001.fastq.gz",
                                   "I16R019a02_01_S3_L002_R1_001.fastq.gz",
                                   "I16R019a02_01_S3_L003_R1_001.fastq.gz",
                                   "I16R019a02_01_S3_L004_R1_001.fastq.gz"].sort()
    }*/

}
