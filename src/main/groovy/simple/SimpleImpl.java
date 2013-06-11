
package simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class SimpleImpl {

	public static void increment(String counterFile) {
		try
		{

            System.out.println(">>> current path is: " + new File(".").getAbsolutePath());

			FileInputStream fis = new FileInputStream(counterFile);
			int count = fis.read();
			fis.close();

			FileOutputStream fos = new FileOutputStream(counterFile);
            fos.write(++count);			
			fos.close();
		}
		catch(FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
}
