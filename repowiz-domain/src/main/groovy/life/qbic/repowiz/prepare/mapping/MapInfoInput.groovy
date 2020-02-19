package life.qbic.repowiz.prepare.mapping

interface MapInfoInput {

    HashMap<String,HashMap> getFields(String uploadType)
    def addOutput(MapInfoOutput output)

}