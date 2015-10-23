package eu.europa.ec.cipa.dispatcher.util;


import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static final Logger logger = Logger.getLogger(PropertiesUtil.class);

    private static Properties properties = null;


    public static final String AS2_ENDPOINT_URL = "as2_endpoint_url";
    public static final String SERVER_MODE = "server_mode";
    public static final String SSL_TRUSTSTORE = "ssl_truststore";
    public static final String SSL_TRUSTSTORE_PASSWORD = "ssl_truststore_password";
    public static final String PARTNER_INTERFACE_IMPLEMENTATION_CLASS = "partner_interface_implementation_class";
    public static final String SEND_INTERFACE_IMPLEMENTATION_CLASS = "send_interface_implementation_class";
    public static final String DB_DRIVER_NAME = "db_driver_name";
    public static final String DB_URL = "db_url";
    public static final String DB_USER = "db_username";
    public static final String DB_PASS = "db_password";
    public static final String AS2_KEYSTORE_PATH = "as2.keystore_path";
    public static final String AS2_KEYSTORE_PASSWORD = "as2.keystore_password";
    public static final String AS2_TRUSTSTORE_PATH = "as2.truststore_path";
    public static final String AS2_TRUSTSTORE_PASSWORD = "as2.truststore_password";
    public static final String AS4_KEYSTORE_PATH = "as4.keystore_path";
    public static final String AS4_KEYSTORE_PASSWORD = "as4.keystore_password";
    public static final String AS4_TRUSTSTORE_PATH = "as4.truststore_path";
    public static final String AS4_TRUSTSTORE_PASSWORD = "as4.truststore_password";
    public static final String DISPATCHER_TRUSTSTORE_PATH = "truststore.location";
    public static final String DISPATCHER_TRUSTSTORE_PASSWORD = "truststore.password";
    public static final String DISPATCHER_KEYSTORE_PATH = "dispatcher.keystore_path";
    public static final String DISPATCHER_KEYSTORE_PASSWORD = "dispatcher.keystore_password";
    public static final String DISPATCHER_CA_ALIAS = "dispatcher.ca_alias";
    public static final String DISPATCHER_ALIAS = "dispatcher.alias";
    public static final String OCSP_RESPONDER_URL = "ocsp_responder_url";
    public static final String OCSP_VALIDATION_ACTIVATED = "ocsp_validation_activated";
    public static final String TEMP_FOLDER_PATH = "temp_folder_path";
    public static final String SMP_MODE = "smp_mode";
    public static final String SMP_URL = "smp_url";
    public static final String SMP_DIRECT_BASIC_AUTH_USERNAME= "smp_direct_mode_basic_auth_username";
    public static final String SMP_DIRECT_BASIC_AUTH_PASSWORD= "smp_direct_mode_basic_auth_password";
    public static final String EBMS_WSDL_PATH = "ebms_wsdl_path";
    public static final String EBMS_ENDPOINT_PREFERENCE_ORDER = "ebms_endpoint";
    public static final String AS2_ENDPOINT_PREFERENCE_ORDER = "as2_endpoint";
    public static final String CACHE_MAX_NUMBER_ENTRIES = "cache_max_number_entries";
    public static final String CACHE_EXPIRE_AFTER_HOURS = "cache_expire_entry_after_hours";
    public static final String AS4_PMODE_FILEPATH = "as4_pmodeFilePath";
    public static final String AS4_ENDPOINT_URL = "as4_endpoint_url";
    public static final String CONFIG_CLASS_PATH = "dispatcher.properties";
    public static final String CONFIG_ALT_PATH = "dispatcher.default.properties";
	public static final String USE_PROXY = "useProxy";
	public static final String PROXY_USER = "proxyUser";
	public static final String PROXY_PASSW = "proxyPassword";
	public static final String HTTP_PROXY_HOST = "http.proxyHost";
	public static final String HTTP_PROXY_PORT = "http.proxyPort";
	public static final String HTTPS_PROXY_HOST = "https.proxyHost";
	public static final String HTTPS_PROXY_PORT = "	https.proxyPort";
	public static final String DNS_ZONE = "sml.dns.zone";

    /**
     * If properties haven't been loaded yet, load them from the configuration file and use the default file location as fallback.
     *
     * @return the loaded properties or null if there was any problem loading the properties file.
     */
    public static Properties getProperties() {
        if (properties == null) {
            try {
                logger.debug("Loading properties from " + CONFIG_CLASS_PATH + "...");
                properties = load(CONFIG_CLASS_PATH);
                if (properties == null) {
                    logger.warn("No configuration file " + CONFIG_CLASS_PATH + " found");
                    logger.debug("Loading properties from " + CONFIG_ALT_PATH + "...");
                    properties = load(CONFIG_ALT_PATH);

                }
                if (properties == null) {
                    logger.error("No configuration file found. Default values will be used. Be warned that the application may not behave correctly if you don't configure the " + CONFIG_CLASS_PATH + " and put it in the classpath");
                }
            } catch (Exception e) {
                properties = null;
            }
        }

        return properties;

    }

    private static Properties load(String configFile) {Properties result = null;
        try {
            InputStream stream = PropertiesUtil.class.getResourceAsStream(configFile);
            Properties props = new Properties();
            props.load(stream);
            stream.close();
            result = props;
        } catch (Exception exc) {
            try {
                InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(configFile);
                Properties props = new Properties();
                props.load(stream);
                stream.close();
                result = props;
            } catch (Exception exc1) {
                try {
                    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
                    Properties props = new Properties();
                    props.load(stream);
                    stream.close();
                    result = props;
                } catch (Exception exc2) {
                    logger.debug("Couldn't load properties: " + configFile);
                }
            }
        }

        if (result != null) {
            logger.debug("Properties loaded from " + configFile);
        }
        return result;
    }
}
