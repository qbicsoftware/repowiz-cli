package life.qbic.repowiz.utils

import groovy.json.JsonSlurper

class IO {

    static List<String> getFilesFromDirectory(String dirPath){

        InputStream repoDir = IO.class.getClassLoader().getResourceAsStream(dirPath)
        //URL repoDir = IO.class.getClassLoader().getResource(dirPath).getPath()
        //File dir = new File(repoDir)
        //println "this is the resource stream $repoDir.text"

        def list = ["clinvar.json","geo.json"]

        repoDir.eachLine { file ->
            list << file
        }

        print list.size() + "list size in getFilesFromDirectory"

        repoDir.close()

        return list
    }

    def static parseJsonFile(File file){
        new JsonSlurper().parseText(file.text)
    }

    def static parseJsonStream(InputStream file){
        new JsonSlurper().parseText(file.text)
    }
}
