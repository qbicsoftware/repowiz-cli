package life.qbic.repowiz.io


//TODO do i really need this?
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
}
