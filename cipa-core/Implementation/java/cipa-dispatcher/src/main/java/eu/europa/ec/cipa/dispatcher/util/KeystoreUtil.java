package eu.europa.ec.cipa.dispatcher.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.Principal;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Properties;

public class KeystoreUtil
{

	private static Properties properties = PropertiesUtil.getProperties(null);
	private KeyStore keyStore = null;
	private String keystorePath;
	private String keystorePwd;
	
	
	public KeystoreUtil() throws Exception
	{
		
		this(properties.getProperty(PropertiesUtil.KEYSTORE_PATH), properties.getProperty(PropertiesUtil.KEYSTORE_PASS));
	}
	
	public KeystoreUtil(String keystorePath, String keystorePwd) throws Exception{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		this.keystorePath = keystorePath;
		this.keystorePwd= keystorePwd;
		
		String[] keystoreTypes = {"pkcs12", "jks", "jceks"};
        
        File inFile = new File(keystorePath);
        FileInputStream inStream = null;
        try
        {
        	if (inFile.exists())
        	{
        		inStream = new FileInputStream(inFile);
        		boolean success = true;
        		for (int i=0 ; i<keystoreTypes.length ; i++)
        		{
        			success = true;
        			if (keystoreTypes[i].equals("pkcs12"))
        				keyStore = KeyStore.getInstance(keystoreTypes[i], "BC");  // BouncyCastle implementation of a keystore allows for the PKCS12 keystore to accept trusted certificates. The default Java provider doesn't.
        			else
        				keyStore = KeyStore.getInstance(keystoreTypes[i]);      //but BouncyCastle doesn't have an implementation for jks! so we try with the default implementation
            		try
            		{
            			keyStore.load(inStream, keystorePwd.toCharArray());
            		}
            		catch (IOException e)
            		{
            			e.printStackTrace();
            			success = false;
            			inStream.close();
            			inStream = new FileInputStream(inFile);  //we close and reopen the stream so it can be read again
            		}
            		if (success) break;
        		}
        		if (!success)
        			throw new Exception("Couldn't load the keystore");
        		
            }
        }
        finally
        {
            if (inStream != null)
                inStream.close();
        }
		
	}
	
	/** Creates a new certificate in the AS2 or AS4 endpoint's truststore
	 * @return
	 */
	public void installNewPartnerCertificate (X509Certificate cert, String alias) throws Exception
	{
		//there shouldn't be two similar PEPPOL cetificate Common Names, so the prefered behaviour is to override if we receive an alias that already exists (we assume there's been a certificate renewal)
		//String uniqueAlias = ensureUniqueAliasName(keyStore, alias);
		keyStore.setCertificateEntry(alias, cert);
		
		FileOutputStream output = new FileOutputStream(keystorePath);
		keyStore.store(output, keystorePwd.toCharArray());
		output.close();
	}
	
	/**	retrieves the PEPPOL AP CA certificate from the keystore
	 */
	public X509Certificate getApCaCertificate() throws Exception
	{
		return (X509Certificate) keyStore.getCertificate(properties.getProperty(PropertiesUtil.KEYSTORE_AP_CA_ALIAS));
	}
	
	
	/** retrieves the AP certificate from the keystore
	 */
	public X509Certificate getApCertificate() throws Exception
	{
		return (X509Certificate) keyStore.getCertificate(properties.getProperty(PropertiesUtil.KEYSTORE_AP_ALIAS));
	}
	
	
	public X509Certificate getCertificateByAlias(String alias) throws Exception
	{
		return (X509Certificate) keyStore.getCertificate(alias);
	}
	
	
    /**Checks that an alias for an import is unique in this keystore*/
    private String ensureUniqueAliasName(KeyStore keystore, String alias) throws Exception
    {
        int counter = 2;
        String newAlias = alias;
        
        while (keystore.containsAlias(newAlias)) {
            newAlias = alias + "_" + counter;
            counter++;
        }
        alias = newAlias;
        return (alias);
    }
	
    
	public static String extractCN(X509Certificate cert)
	{
		Principal principal = cert.getSubjectDN();
		if (principal==null)
			principal = cert.getSubjectX500Principal();
		
		String commonName = null;
		String[] names = principal.getName().split(",");
		for (String s: names)
			if (s.trim().startsWith("CN="))
				commonName = s.trim().substring(3);
		
		return commonName.trim();
	}
    
}
