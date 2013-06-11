/*
 * Copyright (c) 2012, the authors.
 *
 *   This file is part of 'Nextflow'.
 *
 *   Nextflow is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Nextflow is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Nextflow.  If not, see <http://www.gnu.org/licenses/>.
 */

package piper

import com.beust.jcommander.Parameter


/**
 * Simple class holding command line parameters
 *
 * See more http://jcommander.org/
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
class CliOptions {

    @Parameter(names=['--query-file', '-q'])
    String queryFileStr = "./5_RNA_queries.fa"      // args[0]: File with the queries

    @Parameter(names='--genomes-file')
    String genomeFileStr =   "./genomes"               // args[1]: Folder of the genomes

    @Parameter(names='--result-folder')
    String resultFolderStr = "./result"                         // args[2]: Folder for the results

    @Parameter(names='--blast-strategy')
    String blastStrategy =   "ncbi-blast"                                                                          // args[3]: Blast strategy

}
