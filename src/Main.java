import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import constants.GlobalConstants;
import utils.CmdUtil;

/**
 * Created by mcalancea on 2015-08-03.
 */
public class Main {

    public static void main(String[] args){
        System.out.println("Db script wrapper");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date currentDate = new Date();
//        String currentDateString = dateFormat.format(currentDate);

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, "utf-8"));

//            System.out.print("Script Name [" + currentDateString + GlobalConstants.DEFAULT_SCRIPT_NAME_SUFFIX + "]: ");
//            String scriptName = CmdUtil.getValue(bufferedReader.readLine(), currentDateString + DEFAULT_SCRIPT_NAME_SUFFIX);
            System.out.print("Script Name: ");
            String scriptName = bufferedReader.readLine().replaceAll("\\s+","");
            if(scriptName == null || scriptName.isEmpty()) {
                throw new IllegalArgumentException("Script name cannot be empty!");
            }

            System.out.print("Destination folder [" + GlobalConstants.DEFAULT_SCRIPT_DESTINATION_FOLDER + "]: ");
            String desctinationFolder = CmdUtil.getValue(bufferedReader.readLine(), GlobalConstants.DEFAULT_SCRIPT_DESTINATION_FOLDER);
            if(!Files.exists(Paths.get(desctinationFolder))) {
                throw new FileNotFoundException("Could not find this directory: " + desctinationFolder);
            }

            System.out.print("Path to script order file [" + GlobalConstants.DEFAULT_SCRIPT_ORDER_FILE + "]: ");
            String scriptOrderPath = CmdUtil.getValue(bufferedReader.readLine(), GlobalConstants.DEFAULT_SCRIPT_ORDER_FILE);
            if(!Files.exists(Paths.get(scriptOrderPath))) {
                throw new FileNotFoundException("Could not find this file: " + scriptOrderPath);
            }

            System.out.print("Path to script rollback order file [" + GlobalConstants.DEFAULT_SCRIPT_ROLLBACK_ORDER_FILE + "]: ");
            String scriptRollbackOrderPath = CmdUtil.getValue(bufferedReader.readLine(), GlobalConstants.DEFAULT_SCRIPT_ROLLBACK_ORDER_FILE);
            if(!Files.exists(Paths.get(scriptRollbackOrderPath))) {
                throw new FileNotFoundException("Could not find this file: " + scriptRollbackOrderPath);
            }

            BuildDBScripts buildDBScripts = new BuildDBScripts(
                    scriptName,
                    desctinationFolder,
                    scriptOrderPath,
                    scriptRollbackOrderPath
            );
            buildDBScripts.exec();

        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
