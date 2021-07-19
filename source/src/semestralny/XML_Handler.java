/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralny;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Samo
 */
public class XML_Handler {
    
    private static final Logger LOGGER = Logger.getLogger(XML_Handler.class.getName());
    
    public static GameSettings getSettings() throws JAXBException {
        String path = "saves";
        File directory = new File(path);
        if (!directory.exists())
            directory.mkdir();
        
        try {
            File file = new File("saves\\settings.xml");
            JAXBContext context = JAXBContext.newInstance(GameSettings.class);
            
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (GameSettings) unmarshaller.unmarshal(file);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception when reading saved settings:" + e);
            LOGGER.log(Level.INFO, "Attempting to create default settings");
            GameSettings def = GameSettings.getDefaultSettings();
            File file = new File("settings.xml");
            
            if (file.exists())
                file.delete();
            
            file = new File("saves\\settings.xml");
            
            JAXBContext context = JAXBContext.newInstance(GameSettings.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(def, file);
            marshaller.marshal(def, System.out);
            return def;
        }
    }
    
    
    public static void saveSettings(GameSettings settings) throws JAXBException {
        File file = new File("saves\\settings.xml");
            
        JAXBContext context = JAXBContext.newInstance(GameSettings.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(settings, file);
    }
    
   public static GameRules getGameRules(String rules_name) {
        try {
            File file = new File("saves\\gameRules_" + rules_name + ".xml");
            JAXBContext context = JAXBContext.newInstance(GameRules.class);
                    
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (GameRules) unmarshaller.unmarshal(file);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception when reading game rules " + rules_name + " " + e);            
            return null;
        }
    }
    
    public static void saveGameRules(GameRules rules) throws JAXBException {
        File file = new File("saves\\gameRules_" + rules.getName() + ".xml");
        
        JAXBContext context = JAXBContext.newInstance(GameRules.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(rules, file);
    }
    
    private static class gameRuleFilenameFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            return name.startsWith("gameRules_");
        }
        
    }
            
    
    public static ArrayList<GameRules> loadGameRules() {
        ArrayList newList = new ArrayList<>();
        File dir = new File("saves");
        File dir_files[] = dir.listFiles(new gameRuleFilenameFilter());
        
        for (File file: dir_files) {
            String name = file.getName();
            GameRules temp = XML_Handler.getGameRules(name.substring(10, name.length() - 4));
            if (temp != null)
                newList.add(temp);            
        }
        return newList;
    }
}
