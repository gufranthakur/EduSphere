package edusphere;

import java.io.*;

public class DataManager {

    private App app;
    private File outputFile;
    private static final String BASE_PATH = "src/main/resources/data";

    public DataManager(App app, String teacherID, String className) {
        this.app = app;
        File teacherDirectory = new File(BASE_PATH + teacherID);
        if (!teacherDirectory.exists()) teacherDirectory.mkdirs();

        this.outputFile = new File(teacherDirectory, className + ".txt");
    }

    public void writeData(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.write(data);
        } catch (IOException e) {
            app.throwError(e.getMessage());
            throw new RuntimeException("Error writing to file : " + e.getMessage());
        }
    }

    public String readData() {
        String content = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            content = reader.readLine();
        } catch (IOException e) {
            app.throwError(e.getMessage());
            throw new RuntimeException("Error reading to file : " + e.getMessage());
        }
        return content;
    }

    public void switchClass(String teacherID, String className) {
        File teacherDirectory = new File(BASE_PATH + teacherID);
        if (!teacherDirectory.exists()) teacherDirectory.mkdirs();
        this.outputFile = new File(teacherDirectory, className + ".txt");
    }

}
