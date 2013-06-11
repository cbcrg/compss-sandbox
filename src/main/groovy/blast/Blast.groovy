package blast

/**
 * Simple BLAST execution using COMPSS
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
class Blast {

    def static String HOME = System.getenv('HOME')


    static void main(String... args) {

        String query = args?.size()>0 ? args[0] : "./sample.fa"
        String db = args?.size()>1 ? args[1] : "$HOME/tools/blast-db/pdb/pdb"

        println "Starting BLAST -- query: $query"
        def resultFile = new File("result")

        BlastImpl.run(new File(db), new File(query), resultFile)

        println resultFile.text

    }


}
