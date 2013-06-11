package blast

import util.FailedCommandException

/**
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
class BlastImpl {

    def static String BLAST = '/Users/pditommaso/tools/ncbi-blast-2.2.27+/bin/blastp'


    public static void run(File db, File query, File result) {

        def command = "${BLAST} -db ${db.absolutePath} -query ${query.absolutePath} -outfmt 6 > ${result.absolutePath}"

        Process proc = new ProcessBuilder('bash','-c',command).start()
        int exitCode = proc.waitFor()

        if( exitCode ) {
            String message =
                """\
                BLAST returned an invalid exit code: $exitCode

                Command Line:
                    ${command}

                Error message:
                    ${proc.err.text}
                """.stripIndent().toString()

            throw new FailedCommandException(exitCode,message)
        }


    }

}
