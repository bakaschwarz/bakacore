package de.yabue.bakacore.Configurations;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.URL;
import java.util.HashMap;

/**
 * Diese Klasse bietet eine Möglichkeit, Werte aus einer {@code .properties} auszulesen und diese in
 * {@link javafx.beans.value.ObservableValue} zu kapseln.
 * Man kann sowohl die Property, als auch den Wert selbst anfordern.
 * Allerdings müssen Nutzer dieser Klasse wissen, um was für eine Art Wert es sich handelt, wenn sie
 * die Property anfordern.
 */
@Slf4j
public class ObservableConfiguration {

    @Getter
    private final URL CONFIG_PATH;

    private final PropertiesConfiguration PROPERTIES_CONFIGURATION;

    private final HashMap<String, Property> MAP;

    @Getter
    private SimpleBooleanProperty autoSaveProperty;

    /**
     * Konstruiert eine neue Konfigurierung anhand einer übergebenen Properties Datei.
     * @param pathToConfiguration Pfad zu einer Properties Datei.
     * @param autoSave Wenn {@code true}, werden Änderungen an den Properties gespeichert.
     */
    public ObservableConfiguration(@NonNull URL pathToConfiguration, @NonNull boolean autoSave) throws ConfigurationException {
        CONFIG_PATH = pathToConfiguration;
        PROPERTIES_CONFIGURATION = new PropertiesConfiguration(pathToConfiguration);
        PROPERTIES_CONFIGURATION.setAutoSave(autoSave);
        MAP = new HashMap<String, Property>();
        log.debug("Konfiguration initialisiert...");
        autoSaveProperty = new SimpleBooleanProperty(autoSave);
        if(autoSave){
            log.debug("Änderungen an der Konfiguration werden gespeichert.");
        }else{
            log.debug("Änderungen an der Konfiguration werden nicht gespeichert.");
        }
    }

    /**
     * Konstruiert eine leere Konfiguration.
     */
    public ObservableConfiguration(){
        CONFIG_PATH = null;
        PROPERTIES_CONFIGURATION = new PropertiesConfiguration();
        MAP = new HashMap<String, Property>();
        log.debug("Eine leere Konfiguration wurde initialisiert...");
    }

    /**
     * Liefert zu einem übergebenen Schlüssel einen observierbaren Wert zurück. Wird keiner gefunden, so wird mit dem
     * übergebenen Standardwert ein observierbarer Wert erstellt. Diese Methode fügt allerdings keine Werte und Schlüssel
     * permanent in die Properties Datei ein.
     * @param keyWord Der Schlüssel, zu dem ein observierbarer Wert gesucht werden soll.
     * @param defaultValue Ein Wert der genutzt wird, wenn in der Konfigurierungsdatei kein passender Wert oder Schlüssel vorhanden ist.
     * @param <T> Typ des gewünschten Werts.
     * @return Eine Referenz auf einen observierbaren Wert aus der Properties Datei, oder auf den Standartwert.
     */
    public <T> SimpleObjectProperty<T> getObjectProperty(@NonNull final String keyWord, @NonNull T defaultValue){
        SimpleObjectProperty result;
        if(MAP.get(keyWord) == null){
            if(PROPERTIES_CONFIGURATION.getProperty(keyWord) != null){
                result = new SimpleObjectProperty<T>((T) PROPERTIES_CONFIGURATION.getProperty(keyWord));
                log.debug("Schlüssel ist nicht in der Map, füge "+keyWord+" hinzu...");
            }else{
                result = new SimpleObjectProperty<T>(defaultValue);
                log.warn("Schlüssel ist nicht in der Map und der Schlüssel existiert nicht oder hat keinen Wert...");
                log.info("Füge temporär einen Standartwert für "+keyWord+" ein...");
            }
            MAP.put(keyWord, result);
        }else{
            result = (SimpleObjectProperty) MAP.get(keyWord);
            log.debug("Wert für "+keyWord+" gefunden...");
        }
        return result;
    }

    /**
     * Liefert zu einem übergebenen Schlüssel einen observierbaren Wert zurück. Wird keiner gefunden, so wird mit dem
     * übergebenen Standardwert ein observierbarer Wert erstellt. Diese Methode fügt allerdings keine Werte und Schlüssel
     * permanent in die Properties Datei ein.
     * @param keyWord Der Schlüssel, zu dem ein observierbarer Wert gesucht werden soll.
     * @param defaultValue Ein Wert der genutzt wird, wenn in der Konfigurierungsdatei kein passender Wert oder Schlüssel vorhanden ist.
     * @return Eine Referenz auf einen observierbaren Wert aus der Properties Datei, oder auf den Standartwert.
     */
    public SimpleStringProperty getStringProperty(@NonNull final String keyWord, @NonNull String defaultValue){
        SimpleStringProperty result;
        if(MAP.get(keyWord) == null){
            if(PROPERTIES_CONFIGURATION.getProperty(keyWord) != null){
                result = new SimpleStringProperty((String) PROPERTIES_CONFIGURATION.getProperty(keyWord));
                log.debug("Schlüssel ist nicht in der Map, füge "+keyWord+" hinzu...");
            }else{
                result = new SimpleStringProperty(defaultValue);
                log.warn("Schlüssel ist nicht in der Map und der Schlüssel existiert nicht oder hat keinen Wert...");
                log.info("Füge temporär einen Standartwert für "+keyWord+" ein...");
            }
            MAP.put(keyWord, result);
        }else{
            result = (SimpleStringProperty) MAP.get(keyWord);
            log.debug("Wert für "+keyWord+" gefunden...");
        }
        return result;
    }

    /**
     * Liefert zu einem übergebenen Schlüssel einen observierbaren Wert zurück. Wird keiner gefunden, so wird mit dem
     * übergebenen Standardwert ein observierbarer Wert erstellt. Diese Methode fügt allerdings keine Werte und Schlüssel
     * permanent in die Properties Datei ein.
     * @param keyWord Der Schlüssel, zu dem ein observierbarer Wert gesucht werden soll.
     * @param defaultValue Ein Wert der genutzt wird, wenn in der Konfigurierungsdatei kein passender Wert oder Schlüssel vorhanden ist.
     * @return Eine Referenz auf einen observierbaren Wert aus der Properties Datei, oder auf den Standartwert.
     */
    public SimpleIntegerProperty getIntegerProperty(@NonNull final String keyWord, @NonNull Integer defaultValue){
        SimpleIntegerProperty result;
        if(MAP.get(keyWord) == null){
            if(PROPERTIES_CONFIGURATION.getProperty(keyWord) != null){
                result = new SimpleIntegerProperty((Integer) PROPERTIES_CONFIGURATION.getProperty(keyWord));
                log.debug("Schlüssel ist nicht in der Map, füge "+keyWord+" hinzu...");
            }else{
                result = new SimpleIntegerProperty(defaultValue);
                log.warn("Schlüssel ist nicht in der Map und der Schlüssel existiert nicht oder hat keinen Wert...");
                log.info("Füge temporär einen Standartwert für "+keyWord+" ein...");
            }
            MAP.put(keyWord, result);
        }else{
            result = (SimpleIntegerProperty) MAP.get(keyWord);
            log.debug("Wert für "+keyWord+" gefunden...");
        }
        return result;
    }

    /**
     * Liefert zu einem übergebenen Schlüssel einen observierbaren Wert zurück. Wird keiner gefunden, so wird mit dem
     * übergebenen Standardwert ein observierbarer Wert erstellt. Diese Methode fügt allerdings keine Werte und Schlüssel
     * permanent in die Properties Datei ein.
     * @param keyWord Der Schlüssel, zu dem ein observierbarer Wert gesucht werden soll.
     * @param defaultValue Ein Wert der genutzt wird, wenn in der Konfigurierungsdatei kein passender Wert oder Schlüssel vorhanden ist.
     * @return Eine Referenz auf einen observierbaren Wert aus der Properties Datei, oder auf den Standartwert.
     */
    public SimpleDoubleProperty getDoubleProperty(@NonNull final String keyWord, @NonNull Integer defaultValue){
        SimpleDoubleProperty result;
        if(MAP.get(keyWord) == null){
            if(PROPERTIES_CONFIGURATION.getProperty(keyWord) != null){
                result = new SimpleDoubleProperty((Integer) PROPERTIES_CONFIGURATION.getProperty(keyWord));
                log.debug("Schlüssel ist nicht in der Map, füge "+keyWord+" hinzu...");
            }else{
                result = new SimpleDoubleProperty(defaultValue);
                log.warn("Schlüssel ist nicht in der Map und der Schlüssel existiert nicht oder hat keinen Wert...");
                log.info("Füge temporär einen Standartwert für "+keyWord+" ein...");
            }
            MAP.put(keyWord, result);
        }else{
            result = (SimpleDoubleProperty) MAP.get(keyWord);
            log.debug("Wert für "+keyWord+" gefunden...");
        }
        return result;
    }


}
