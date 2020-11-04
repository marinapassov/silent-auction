package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

final public class PropertiesHolder {

    private Properties propertiesInst;
    private static PropertiesHolder propertiesHolderInst;

    private PropertiesHolder(){
        //read from properties file
        createPropertiesHolderInst();
    }

    public static PropertiesHolder getPropertiesHolderInst()
    {
        if (propertiesHolderInst == null)
        {
            propertiesHolderInst = new PropertiesHolder();
        }

        return propertiesHolderInst;
    }

    private void createPropertiesHolderInst()
    {
        final String propFileName = "config.properties";
        InputStream inputStream = null;

        if (propertiesInst == null) {

            try {
                propertiesInst = new Properties();

                inputStream = PropertiesHolder.class.getClassLoader().getResourceAsStream(propFileName);

                if (inputStream != null) {
                    propertiesInst.load(inputStream);

                } else {
                    //TODO: verify
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        //TODO: verify
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public String getPropertyString(String propertyName)
    {

        return propertiesInst.getProperty(propertyName);
    }

    public int getPropertyInt(String propertyName)
    {
        return Integer.parseInt(propertiesInst.getProperty(propertyName));
    }

    public boolean getPropertyBool(String propertyName)
    {
        return Boolean.parseBoolean(propertiesInst.getProperty(propertyName));
    }

}
