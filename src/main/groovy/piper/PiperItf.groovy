package piper

import integratedtoolkit.types.annotations.Method
import integratedtoolkit.types.annotations.Parameter
/**
 * Created with IntelliJ IDEA.
 * User: bsanjuan
 * Date: 5/31/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PiperItf {
    @Method(declaringClass = "piper.PiperImpl")
    void createBlastDatabase(
            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            String strategy,

            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            File genomeFa,

            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            String db_folder
    );

    @Method(declaringClass = "piper.PiperImpl")
    void createChrDatabase(
            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            File genomeFa,

            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            String chr_folder
    );

    @Method(declaringClass = "piper.PiperImpl")
    void blastRun(
            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            String strategy,

            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            String blastDatabase,

            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            String queryFile,

            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            String blastResult
    );

    @Method(declaringClass = "piper.PiperImpl")
    void exonerateRun(
            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            String queryFile,

            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            String mf2File,

            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            String chr,

            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            String resultFolder,

            @Parameter(type = Parameter.Type.STRING, direction = Parameter.Direction.IN)
            String specie
    );



}
