package piper

//import util.FailedCommandException


/**
 * Created with IntelliJ IDEA.
 * User: bsanjuan
 * Date: 5/27/13
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class PiperImpl {

    def static String split = (System.properties['os.name'] == 'Mac OS X' ? 'gcsplit' : 'csplit')


    public static void createBlastDatabase(String strategy, String genomeFa, String db_folder) {

        def command =
            """
            if [[ ! `ls -A ${db_folder} 2>/dev/null` ]]; then

            ## Create the target folder
            mkdir -p ${db_folder}

            ## Format the BLAST DB
            /users/cn/bmartin/Software/compss-sandbox/bin/x-format.sh ${strategy} ${genomeFa} ${db_folder}
            fi
            """

        Process proc = new ProcessBuilder("bash","-c",command).start()
        int exitCode = proc.waitFor()
        //exceptionCatcher(exitCode,command,proc)
    }


    public static void createChrDatabase(String genomeFa, String chr_folder) {

        def command =
            """
            if [[ ! `ls -A ${chr_folder} 2>/dev/null` ]]; then

                ## split the fasta in a file for each sequence 'seq_*'
                $split ${genomeFa} '%^>%' '/^>/' '{*}' -f seq_ -n 5

                ## create the target folder
                mkdir -p ${chr_folder}

                ## rename and move to the target folder
                for x in seq_*; do
                    SEQID=`grep -E "^>" \$x | sed 's/^>\\(\\S*\\\\).*/\\1/'  | sed 's/[\\>\\<\\/\\''\\:\\\\]/_/'`
                    mv \$x ${chr_folder}/\$SEQID;
                done
            fi
            """

        Process proc = new ProcessBuilder("bash","-c",command).start()
        int exitCode = proc.waitFor()
        //exceptionCatcher(exitCode,command,proc)
    }


    public static void blastRun(String strategy, String blastDatabase, String queryFile, String blastResult) {

        def command = """/users/cn/bmartin/Software/compss-sandbox/bin/x-blast.sh ${strategy} ${blastDatabase} ${queryFile} > ${blastResult}"""

        Process proc = new ProcessBuilder("bash","-c",command).start()
        int exitCode = proc.waitFor()
        //exceptionCatcher(exitCode,command,proc)
    }


    public static void exonerateRun(String queryFile, String mf2File, String chr, String resultFolder, String specie){

        def command =
            """
            ## apply exonerate
            /users/cn/bmartin/Software/compss-sandbox/bin/exonerateRemapping.pl -query ${queryFile} -mf2 ${mf2File} -targetGenomeFolder ${chr} -exonerate_lines_mode 1000 -exonerate_success_mode 1 -ner no
            if [ ! -s blastResult.fa ]; then exit 0; fi

            ## exonerateRemapping create a file named 'blastResult.fa'
            ## split the exonerate result into single files
            ${split} blastResult.fa '%^>%' '/^>/' '{*}' -f .seq_ -n 5
            mv blastResult.fa .blastResult.fa

            ## rename the seq_xxx files so that the file name match the seq fasta id
            ## plus append the specie to th sequence id
            for x in .seq_*; do
              SEQID=`grep '>' \$x`
              FILENAME=`grep '>' \$x | sed 's/^>\\(.*\\)_hit\\d*.*\$/\\1/'`
              printf "\${SEQID}_\${specie}\\n" >> \${FILENAME}.fa
              cat \$x | grep -v '>' >> \${FILENAME}.fa
            done

            ## create a directory for the results: specie.ex.gtf
            mkdir -p ${resultFolder}
            mv blastResult.ex.gtf ${resultFolder}/${specie}.ex.gtf

            ## create a directory for the hits' fasta files
            mkdir -p ${resultFolder}/hits
            mv *.fa ${resultFolder}/hits
            """

        Process proc = new ProcessBuilder("bash","-c",command).start()
        int exitCode = proc.waitFor()
       // exceptionCatcher(exitCode,command,proc)
    }


    /*   public static void exceptionCatcher(int exitCode, def command, Process proc){

       if( exitCode ) {

           String message =
               """\
               BLAST returned an invalid exit code: $exitCode

               Command Line:
               ${command}

               Error message:
               ${proc.err.text}
               """.stripIndent().toString()

           println(message)
          // throw new FailedCommandException(exitCode,message)
       }
   }    */


}
