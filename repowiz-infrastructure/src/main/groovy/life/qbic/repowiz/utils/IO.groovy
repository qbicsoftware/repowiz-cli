package life.qbic.repowiz.utils

import groovy.json.JsonSlurper

class IO {

    def static getFilesFromDirectory(String dirPath){

        InputStream repoDir = IO.class.getClassLoader().getResourceAsStream(dirPath)
        //URL repoDir = IO.class.getClassLoader().getResource(dirPath).getPath()
        //File dir = new File(repoDir)

        def list = []

        repoDir.eachLine { file ->
            list << file
        }

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
