package tests.MockModels;

import backend.FilesBackend;
import model.Profile;

import java.io.File;
import java.io.IOException;

public class FilesBackendMock extends FilesBackend {
    private final String testDir = "tests_output";

    @Override
    protected File getProfileFile() {
        var profileDirectory = new File(String.format("%s%s%s",
                getOsSpecificAppPath(), osBackslash(), testDir));
        profileDirectory.mkdirs();
        var profileFile = new File(String.format("%s%s%s.txt", profileDirectory.getPath(), osBackslash(), Profile.class.getSimpleName().toLowerCase()));
        try {
            if(!profileFile.exists()){
                profileFile.createNewFile();
            }
        }
        catch (IOException ex){
            throw new RuntimeException("IO exception reading profile file\n" + ex);
        }
        return profileFile;
    }

    @Override
    protected File getReceiptDirectory() {
        var directory = new File(String.format("%s%s%s%s%s",
                getOsSpecificAppPath(), osBackslash(), testDir, osBackslash(), "Receipts"));
        directory.mkdirs();
        return directory;
    }

    @Override
    protected File getCartFile() {
        var directoryPath = new File(getOsSpecificAppPath());
        var file = new File(String.format("%s%s%s%s%s",
                directoryPath.getPath(), osBackslash(), testDir, osBackslash(), "cart.txt"));

        directoryPath.mkdirs();
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        }
        catch (Exception ex){
            throw new RuntimeException("failed to create crate file" + ex);
        }

        return file;
    }
}
