package compss.piper

/**
 * Created with IntelliJ IDEA.
 * User: bsanjuan
 * Date: 5/27/13
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
class PiperMain {

    File targetFolder = new File('./db')



    static void main(String[] args) {

        String queryFileStr = args[0]
        String genomeFileStr = args[1]


        List<File> allGenomes = null // .. to do


        /*
         * create BLAST DB
         */

        allGenomes.each {

            PiperTasks.createBlastDatabase('ncbi-blast', it, targetFolder )

        }


        /*
         * Create Chromosome DB
         */

        /*
         * Blast stage
         */


        /*
         * Exonerate stage ..
         *
         */





    }

}
