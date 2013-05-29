package compss.piper

/**
 * Created with IntelliJ IDEA.
 * User: bsanjuan
 * Date: 5/27/13
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
class PiperTasks {

    static void createBlastDatabase( String strategy, File genomeFile, File db_folder ) {

        if(!db_folder.exists()){
            db_folder.mkdir()
            "./bin/x-format.sh $strategy $genomeFile $db_folder".execute().waitFor()
        }
    }


    static void createChrDatabase( File genomeFile, File chr_folder ) {
        def split = (System.properties['os.name'] == 'Mac OS X' ? 'gcsplit' : 'csplit')

        if(!chr_folder.exists()){
            chr_folder.mkdir()
            "$split $genomeFile %^>%  /^>/ {*} -f seq_ -n 5".execute().waitFor()

            new File("." ).eachFile{ file ->
                if(file =~ /seq_*/){
                    String line
                    file.withReader { line = it.readLine().substring(1) }
                    file.renameTo("./$line")
                    "mv $line $chr_folder/$line".execute().waitFor()
                }
            }
        }

    }

    static void blastRun( String blastStrategy, File blastDatabase, File queryFile, File blastResult  ) {
        def process = "./bin/x-blast.sh $blastStrategy $blastDatabase $queryFile".execute()

        def ln = System.getProperty('line.separator')
        process.text.eachLine { line ->
            blastResult.append("$line$ln")
        }
    }

    static void exonerateRun( File queryFile, File mf2File, File chrDatabasePath, List<File> exonerateOut, List<File> exonerateGtf  ) {


    }


}
