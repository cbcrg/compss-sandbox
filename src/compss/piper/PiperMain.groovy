package compss.piper

/**
 * Created with IntelliJ IDEA.
 * User: bsanjuan
 * Date: 5/27/13
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
class PiperMain {

   static File targetFolder = new File("/users/cn/bsanjuan/Software/compss-sandbox/src/compss/db")     // Directorio para nuestra base de datos



    static void main(String[] args) {

        String queryFileStr = "/users/cn/bsanjuan/Software/compss-sandbox/src/compss/tutorial/5_RNA_queries.fa"//args[0]             // Fichero que contiene las secuencias query
        String genomeFileStr = '/users/cn/bsanjuan/Software/compss-sandbox/src/compss/tutorial/genomes' //args[1]            // Carpeta donde se encuentran los genomas a usar
        String blastStrategy = 'ncbi-blast' //args[2]            // Estrategia a seguir por blast

        if(!targetFolder.exists()){
            if(!targetFolder.mkdirs())
                exit 1, "Cannot create genomes-db path: $targetFolder -- check file system permissions"
        }

        // List<File> allGenomes = null // .. to do           // Lista de todos los genomas a usar en la busqueda
        Map allGenomes = parseGenomesFolder(genomeFileStr,blastStrategy)


        /*
         * create BLAST DB
         */
        allGenomes.each { name, entry ->
            File genome_folder = new File("$targetFolder.absoluteFile/$name")
            if (!genome_folder.exists())
                genome_folder.mkdir()

            PiperTasks.createBlastDatabase(blastStrategy, entry['genome_fa'], entry['blast_db'])
        }


        /*
         * Create Chromosome DB
         */
        allGenomes.each { name, entry ->
            PiperTasks.createChrDatabase(entry['genome_fa'],entry['chr_db'])
        }


        /*
         * Blast stage
         */
         allGenomes.each { name, entry ->
            File blastFile = new File("$targetFolder/$name/resultado")
            File queryFile = new File(queryFileStr)

            PiperTasks.blastRun(blastStrategy,entry['blast_db'],queryFile,blastFile)
         }

        /*
         * Exonerate stage ..
         *
         */
    }

     static Map parseGenomesFolder(String genomeFile, String blast){
         def result = [:]

         def genomes = new File(genomeFile)
         if(!genomes.exists())
               exit 2, "Error: no existe una carpeta con genomas"

         genomes.eachDir { File path ->
            def fasta = path.listFiles().find { File file -> file.name.endsWith('.fa')}
            if(fasta){
                result[path.name] = [
                        genome_fa: fasta,
                        chr_db: new File("$targetFolder/$path.name/chr"),
                        blast_db: new File("$targetFolder/$path.name/$blast-db")
                ]
            }
         }
         return result
     }

}
