package life.qbic.repowiz.model


class GeoSample {

    String sampleName
    HashMap<String,String> properties
    List<String> rawFiles

    GeoSample(String name, HashMap sampleProperties, List<String> raws){
        sampleName = name
        this.properties = sampleProperties
        this.rawFiles = raws
    }

}
