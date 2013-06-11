package blast

import integratedtoolkit.types.annotations.Method
import integratedtoolkit.types.annotations.Parameter

/**
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
public interface BlastItf {


    @Method(declaringClass = "blast.BlastImpl")
    void run(
            @Parameter(type = Parameter.Type.FILE, direction = Parameter.Direction.IN)
            File db,

            @Parameter(type = Parameter.Type.FILE, direction = Parameter.Direction.IN)
            File query,

            @Parameter(type = Parameter.Type.FILE, direction = Parameter.Direction.OUT)
            File result
    )


}