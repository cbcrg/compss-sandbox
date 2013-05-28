package compss.piper

/**
 * Created with IntelliJ IDEA.
 * User: bsanjuan
 * Date: 5/27/13
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
class PiperMain {

   static File targetFolder = new File("/users/cn/bsanjuan/Software/compss-sandbox/src/compss/db") //= new File("/users/cn/bsanjuan/Software/db").mkdir()      // Directorio para nuestra base de datos



    static void main(String[] args) {

        String queryFileStr = "/users/cn/bsanjuan/Software/compss-sandbox/src/compss/tutorial/5_RNA_queries.fa"//args[0]             // Fichero que contiene las secuencias query
        String genomeFileStr = '/users/cn/bsanjuan/Software/compss-sandbox/src/compss/tutorial/genomes' //args[1]            // Carpeta donde se encuentran los genomas a usar
        String blastStrategy = 'ncbi-blast' //args[2]            // Estrategia a seguir por blast

        if(!targetFolder.exists()){
            targetFolder.mkdir()
        }

        // List<File> allGenomes = null // .. to do           // Lista de todos los genomas a usar en la busqueda
        Map allGenomes = parseGenomesFolder(genomeFileStr,blastStrategy)

        /*
         * create BLAST DB
         */

        allGenomes.each { name, entry ->
            println("Name: $name , Fasta: $entry")

            File genome_folder = new File("$targetFolder.absoluteFile/$name")
            if (!genome_folder.exists()){
                genome_folder.mkdir()
            }

            PiperTasks.createBlastDatabase(blastStrategy, entry, genome_folder)
            PiperTasks.createChrDatabase(entry,genome_folder)
        }


        /*
         * Create Chromosome DB
         */
  /*      allGenomes.each {
            // Mando => it: fichero fasta del genoma  ;  targetFolder: directorio en donde estan los genomas
            PiperTasks.createChrDatabase(it,targetFolder)

        }   */

        /*
         * Blast stage
         */


        /*
         * Exonerate stage ..
         *
         */
    }

     static Map parseGenomesFolder(String genomeFile, String blast){
        def result = [:]

         def genomes = new File(genomeFile)
         if(!genomes.exists())
               println("No existe esto")

         genomes.eachDir { File path ->
            def fasta = path.listFiles().find { File file -> file.name.endsWith('.fa')}
            if(fasta){
                result[path.name] = fasta
            //    new File("targetFolder.absoluteFile/$path.name/chr")
            //    new File("targetFolder.absoluteFile/$path.name/$blast-db")
            }

        }
        return result

    }

}
