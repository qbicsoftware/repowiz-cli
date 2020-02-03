package life.qbic.repowiz.utils

import groovy.io.FileType
import groovy.json.JsonSlurper

class IO {

    def static getFilesFromDirectory(InputStream dir){
        def list = []

        dir.eachLine { file ->
            list << file
        }

        dir.close()

        list
    }

    def static getFileFromFileList(List files, String repository){
        //find file containing the information for the defined repository
    }

    def static parseJSON(File file){
        new JsonSlurper().parseText(file.text)
    }
}
