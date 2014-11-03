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
    public ObservableConfiguration(@NonNull URL pathToConfiguration, boolean autoSave) throws ConfigurationException {
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
                result = new SimpleIntegerProperty(Integer.parseInt((String) PROPERTIES_CONFIGURATION.getProperty(keyWord)));
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
    public SimpleDoubleProperty getDoubleProperty(@NonNull final String keyWord, @NonNull Double defaultValue){
        SimpleDoubleProperty result;
        if(MAP.get(keyWord) == null){
            if(PROPERTIES_CONFIGURATION.getProperty(keyWord) != null){
                result = new SimpleDoubleProperty(Double.parseDouble((String) PROPERTIES_CONFIGURATION.getProperty(keyWord)));
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

    /**
     * Setzt einen Wert für ein übergebenes Schlüsselwort.
     * @param keyWord Der Schlüssel, zu dem ein observierbarer Wert zugewiesen werden soll.
     * @param value Der Wert, der dem Schlüssel zugewiesen werden soll.
     */
    public SimpleStringProperty setStringProperty(@NonNull final String keyWord, @NonNull final String value){
        SimpleStringProperty property;
        if( PROPERTIES_CONFIGURATION.getProperty(keyWord) == null){
            property = new SimpleStringProperty(value);
            MAP.put(keyWord, property);
        }else{
            property = getStringProperty(keyWord, value);
        }
        PROPERTIES_CONFIGURATION.setProperty(keyWord, value);
        log.debug("Setze neuen Wert für "+keyWord+"...");
        return property;
    }

    /**
     * Setzt einen Wert für ein übergebenes Schlüsselwort.
     * @param keyWord Der Schlüssel, zu dem ein observierbarer Wert zugewiesen werden soll.
     * @param value Der Wert, der dem Schlüssel zugewiesen werden soll.
     */
    public SimpleIntegerProperty setIntegerProperty(@NonNull final String keyWord, @NonNull final Integer value){
        SimpleIntegerProperty property;
        if( PROPERTIES_CONFIGURATION.getProperty(keyWord) == null){
            property = new SimpleIntegerProperty(value);
            MAP.put(keyWord, property);
        }else{
            property = getIntegerProperty(keyWord, value);
        }
        PROPERTIES_CONFIGURATION.setProperty(keyWord, value);
        log.debug("Setze neuen Wert für "+keyWord+"...");
        return property;
    }

    /**
     * Setzt einen Wert für ein übergebenes Schlüsselwort.
     * @param keyWord Der Schlüssel, zu dem ein observierbarer Wert zugewiesen werden soll.
     * @param value Der Wert, der dem Schlüssel zugewiesen werden soll.
     */
    public SimpleDoubleProperty setDoubleProperty(@NonNull final String keyWord, @NonNull final Double value){
        SimpleDoubleProperty property;
        if( PROPERTIES_CONFIGURATION.getProperty(keyWord) == null){
            property = new SimpleDoubleProperty(value);
            MAP.put(keyWord, property);
        }else{
            property = getDoubleProperty(keyWord, value);
        }
        PROPERTIES_CONFIGURATION.setProperty(keyWord, value);
        log.debug("Setze neuen Wert für "+keyWord+"...");
        return property;
    }

    /**
     * Setzen dieses Wertes entscheidet, ob Änderungen an der Properties Datei permanent sind, oder nicht.
     * @param autoSave {@code true}, wenn Änderungen gespeichert werden sollen.
     */
    public void setAutoSaveProperty(final boolean autoSave){
        autoSaveProperty.setValue(autoSave);
        log.debug("Speicherfunktion der Konfiguration wird auf "+Boolean.toString(autoSave)+" gesetzt...");
    }
}
