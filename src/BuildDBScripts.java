import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import constants.GlobalConstants;
import constants.ScriptConstants;
import utils.FileUtils;
import validation.Rule;
import validation.ValidationRules;
import validation.errors.ErrorCode;
import validation.errors.ErrorConverter;

/**
 * Created by mcalancea on 2015-08-03.
 */
public class BuildDBScripts {

    private final String fileWithScriptOrder;
    private final String fileWithRollbackScriptOrder;
    private final String destinationFolder;
    private final String scriptName;

    private final List<String> scriptOrder = new ArrayList<>();
    private final List<String> rollbackScriptOrder = new ArrayList<>();
    private final String PREFIX = "@";

    private String pathForScript;


    public BuildDBScripts(String scriptName,
            String destinationFolder,
            String fileWithScriptOrder,
            String fileWithRollbackScriptOrder
            ){
        this.fileWithScriptOrder = fileWithScriptOrder;
        this.fileWithRollbackScriptOrder = fileWithRollbackScriptOrder;
        this.destinationFolder = destinationFolder;
        this.scriptName = scriptName;
    }

    public static void main(String[] args) {

        String fileWithScriptOrder = "C:\\acquisio\\order_file.txt";
        String fileWithRollbackScriptOrder = "C:\\acquisio\\rollback_order_file.txt";
        String pathToWriteFolder = "C:\\acquisio\\";
        String scriptName = "!!!_test_script";

        BuildDBScripts dbScripts = new BuildDBScripts(scriptName, pathToWriteFolder, fileWithScriptOrder, fileWithRollbackScriptOrder);
        dbScripts.exec();
    }

    private String getPathForScript(){
        if(pathForScript == null) {
            pathForScript = destinationFolder + File.separator + scriptName;
        }
        return pathForScript;
    }

    public void exec() {
        readOrderFiles();
        validate();
        try {
            writeDbScript();
            writeRollbackDbScript();
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    private void readOrderFiles(){
        FileUtils.readOrderFile(fileWithScriptOrder, scriptOrder);
        FileUtils.readOrderFile(fileWithRollbackScriptOrder, rollbackScriptOrder);
    }

    private void prefixScriptNames(List<String> scriptList, StringBuilder scriptsWithPrefix, StringBuilder scriptsWithDoublePrefix){
        for (String scriptName : scriptList) {
            Multimap<ErrorCode, Object> errorCodes = validateScriptNames(scriptName);
            if(!errorCodes.isEmpty()) {
                throw new IllegalArgumentException(ErrorConverter.convertErrorCodeToString(errorCodes));
            }

            scriptsWithPrefix.append(PREFIX);
            scriptsWithPrefix.append(scriptName);
            scriptsWithPrefix.append("\n");

            // Pas de synonyme dans les schemas de developpeurs.
            if(!scriptName.toLowerCase().contains("synonym")) {
                scriptsWithDoublePrefix.append(PREFIX);
                scriptsWithDoublePrefix.append(PREFIX);
                scriptsWithDoublePrefix.append(scriptName);
                scriptsWithDoublePrefix.append("\n");
            }
        }
    }

    private void validate(){
        Multimap<ErrorCode, Object> errorCodes = ArrayListMultimap.create();
        for (String scriptName : scriptOrder) {
            errorCodes.putAll(validateScriptNames(scriptName));
        }
        for (String scriptName : rollbackScriptOrder) {
            errorCodes.putAll(validateScriptNames(scriptName));
        }
        if(!errorCodes.isEmpty()) {
            throw new IllegalArgumentException(ErrorConverter.convertErrorCodeToString(errorCodes));
        }
    }

    private Multimap<ErrorCode, Object> validateScriptNames(String scriptName) {
        Multimap<ErrorCode, Object> errorCodes = ArrayListMultimap.create();
        for (ValidationRules validationRules : ValidationRules.values()) {
            Rule rule = validationRules.getRule();
            rule.setObjectToValidate(scriptName);
            errorCodes.putAll(rule.validate());
        }
        return errorCodes;
    }

    private void wrapDbScript(String path, List<String> scriptList){
        StringBuilder scriptsWithPrefix = new StringBuilder();
        StringBuilder scriptsWithDoublePrefix = new StringBuilder();
        prefixScriptNames(scriptList, scriptsWithPrefix, scriptsWithDoublePrefix);

        String newStat = ScriptConstants.STAT
                .replace(ScriptConstants.KEY_WORD, scriptsWithDoublePrefix.toString())
                .replace(ScriptConstants.SCRIPT_NAME_PLACEHOLDER, scriptName);

        String newNonStat = ScriptConstants.NON_STAT.replace(ScriptConstants.KEY_WORD, scriptsWithPrefix.toString());

        FileUtils.writeToFile(path + File.separator + ScriptConstants.STAT_FILE_NAME, newStat);

        for (int i = 1; i <= GlobalConstants.PARTITION_QTY; i++) {
            String nonStatFileName = ScriptConstants.NON_STAT_FILE_NAME.replace(ScriptConstants.PARTITION_NUMBER_KEY_WORD, Integer.toString(i));
            String nonStatScript = newNonStat
                    .replace(ScriptConstants.PARTITION_NUMBER_KEY_WORD, Integer.toString(i))
                    .replace(ScriptConstants.SCRIPT_NAME_PLACEHOLDER, scriptName);
            FileUtils.writeToFile(path + File.separator + nonStatFileName, nonStatScript);
        }

//        //prod
//        FileUtils.copyFileFromJar(getClass().getClassLoader().getResourceAsStream("cc_grant_obj_privileges.sql"),
//                path + "\\cc_grant_obj_privileges.sql");
//        FileUtils.copyFileFromJar(getClass().getClassLoader().getResourceAsStream("validate_invalid_objects.sql"),
//                path + "\\validate_invalid_objects.sql");
        //test
        FileUtils.copyFileFromJar("cc_grant_obj_privileges.sql",
                path + "\\cc_grant_obj_privileges.sql");
        FileUtils.copyFileFromJar("validate_invalid_objects.sql",
                path + "\\validate_invalid_objects.sql");
    }

    private void writeDbScript() throws FileAlreadyExistsException{
        if(FileUtils.createDirectory(getPathForScript())) {
            wrapDbScript(getPathForScript(), scriptOrder);
        } else {
            throw new FileAlreadyExistsException("directory "+getPathForScript()+" already exists!!!");
        }
    }

    private void writeRollbackDbScript() throws FileAlreadyExistsException {
        if(!rollbackScriptOrder.isEmpty()) {
            String pathForRollbackScript = getPathForScript() + File.separator + "ROLLBACK";
            if (Files.exists(Paths.get(getPathForScript())) && FileUtils.createDirectory(pathForRollbackScript)) {
                wrapDbScript(pathForRollbackScript, rollbackScriptOrder);
            } else {
                throw new FileAlreadyExistsException("directory " + pathForRollbackScript + " already exists!!!");
            }
        }
    }

}
