package utilities;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public  class FileUpload {

    public static void fileUpload() throws InterruptedException, FindFailed {



        Screen s=new Screen();

        Pattern fileInputBox=new Pattern(System.getProperty("user.dir")+"//resources/fileupload/fileInputBox.png");

        Pattern openButton=new Pattern(System.getProperty("user.dir")+"//resources/fileupload/openButton.png");

        Thread.sleep(5000);

        s.wait(fileInputBox,20);


        s.type(fileInputBox,System.getProperty("user.dir")+"\\resources\\fileupload\\persons.csv");
        s.click(openButton);




    }
}
