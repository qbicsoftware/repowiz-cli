package life.qbic.repowiz.utils

import groovy.json.JsonSlurper

class IO {

    static List<String> getFilesFromDirectory(String dirPath){

        def list = ["clinvar.json","geo.json"]

        /**
         * InputStream repoDir = IO.class.getClassLoader().getResourceAsStream(dirPath)
         * list = []
         * repoDir.eachLine { file ->
            list << file
        }
        repoDir.close()
         */

        return list
    }

    def static parseJsonFile(File file){
        new JsonSlurper().parseText(file.text)
    }

    def static parseJsonStream(InputStream file){
        new JsonSlurper().parseText(file.text)
    }
}
