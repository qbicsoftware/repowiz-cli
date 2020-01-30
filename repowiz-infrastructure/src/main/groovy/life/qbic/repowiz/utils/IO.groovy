package life.qbic.repowiz.utils

import groovy.io.FileType
import groovy.json.JsonSlurper

class IO {

    def static getFilesFromDirectory(File dir){
        def list = []

        dir.eachFileRecurse (FileType.FILES) { file ->
            list << file
        }

        list
    }

    def static getFileFromFileList(List files, String repository){
        //find file containing the information for the defined repository
    }

    def static parseJSON(File file){
        new JsonSlurper().parseText(file.text)
    }
}
