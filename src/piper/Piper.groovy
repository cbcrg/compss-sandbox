package piper

/**
 * Created with IntelliJ IDEA.
 * User: bsanjuan
 * Date: 5/27/13
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 *
 */

public class Piper {

    def static File targetFolder = new File("/users/cn/bmartin/Software/compss-sandbox/src/compss/db")                 // Main directory for the databases

    public static void main(String[] args) {

        String queryFileStr =    "/users/cn/bmartin/Software/compss-sandbox/src/compss/tutorial/5_RNA_queries.fa"      // args[0]: File with the queries
        String genomeFileStr =   "/users/cn/bmartin/Software/compss-sandbox/src/compss/tutorial/genomes"               // args[1]: Folder of the genomes
        String resultFolderStr = "/users/cn/bmartin/Software/compss-sandbox/src/compss/result"                         // args[2]: Folder for the results
        String blastStrategy =   "ncbi-blast"                                                                          // args[3]: Blast strategy


        if(!targetFolder.exists()){
            if(!targetFolder.mkdirs())
                exit 1, "Cannot create genomes-db path: $targetFolder -- check file system permissions"
        }

        // List of genomes
        Map allGenomes = parseGenomesFolder(genomeFileStr,blastStrategy)


        allGenomes.each { name, entry ->
            PiperImpl.createBlastDatabase(blastStrategy, entry['genome_fa'], entry['blast_db'])
        }


        /*
         * Create BLAST DB
         */
        allGenomes.each { name, entry ->
            PiperImpl.createBlastDatabase(blastStrategy, entry['genome_fa'], entry['blast_db'])
        }


        /*
         * Create Chromosome DB
         */
        allGenomes.each { name, entry ->
            PiperImpl.createChrDatabase(entry['genome_fa'],entry['chr_db'])
        }


        /*
         * Blast stage
         */
         allGenomes.each { name, entry ->
            String blastFile =  "$targetFolder/$name/blastResult"

            PiperImpl.blastRun(blastStrategy,entry['blast_db'],queryFileStr,blastFile)
         }


        /*
         * Exonerate stage ..
         *
         */
        allGenomes.each { name, entry ->
            String blastFile = "$targetFolder/$name/blastResult"

            PiperImpl.exonerateRun(queryFileStr,blastFile,entry['chr_db'],resultFolderStr,name)
        }

    }


    /*
     * Parse the genome folder
     *
     */
     public static Map parseGenomesFolder(String genomeFile, String blast){
         def result = [:]

         def genomes = new File(genomeFile)
         if(!genomes.exists())
               exit 2, "Cannot read genomes-folder path: $genomeFile -- check file system permissions"

         genomes.eachDir { File path ->
            def fasta = path.listFiles().find { File file -> file.name.endsWith('.fa')}
            if(fasta){
                result[path.name] = [
                        genome_fa: fasta.path,
                        chr_db:   "$targetFolder/$path.name/chr",
                        blast_db: "$targetFolder/$path.name/$blast-db"
                ]
            }
         }
         return result
     }


}
