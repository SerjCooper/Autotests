package Autotests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class InitConfig {

    public static String browserName;
    public static String targetUrl;
    public static String driverPath;

    private static String CONFIG_PATH = "./src/test/resources/config.json";

    public InitConfig() { //метод для чтения json

        JsonParser parser = new JsonParser();

        browserName = null;
        targetUrl = null;
        driverPath = null;
        try {
            System.out.println("Читаем файл...");
            JsonArray jArray = (JsonArray) parser.parse(new FileReader(CONFIG_PATH));
            for (Object o : jArray) {
                JsonObject root = (JsonObject) o;
                browserName = root.get("browserName").getAsString();
                targetUrl = root.get("targetUrl").getAsString();
                driverPath = root.get("driverPath").getAsString() + browserName + "driver.exe";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
