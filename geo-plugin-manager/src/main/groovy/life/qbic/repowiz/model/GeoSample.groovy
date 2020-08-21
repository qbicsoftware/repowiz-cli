package life.qbic.repowiz.model


class GeoSample {

    String sampleName
    HashMap<String, String> properties
    List<String> rawFiles
    HashMap characteristics

    GeoSample(String name, HashMap sampleProperties, List<String> raws, HashMap characteristics) {
        sampleName = name
        this.properties = sampleProperties
        this.rawFiles = raws
        this.characteristics = characteristics
    }

}
