/**
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package eu.europa.ec.cipa.smp.server.util;

import com.helger.commons.charset.CCharset;
import com.helger.commons.exceptions.InitializationException;
import com.helger.commons.io.streams.StringInputStream;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import eu.europa.ec.cipa.smp.server.security.KeyStoreUtils;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.spi.ContainerResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Nonnull;
import javax.annotation.Priority;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

/**
 * This class adds a XML DSIG to successful GET's for SignedServiceMetadata
 * objects.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Priority(value = 2000)
public final class SignatureFilter implements ContainerResponseFilter {
  public static final String CONFIG_XMLDSIG_KEYSTORE_CLASSPATH = "xmldsig.keystore.classpath";
  public static final String CONFIG_XMLDSIG_KEYSTORE_PASSWORD = "xmldsig.keystore.password";
  public static final String CONFIG_XMLDSIG_KEYSTORE_KEY_ALIAS = "xmldsig.keystore.key.alias";
  public static final String CONFIG_XMLDSIG_KEYSTORE_KEY_PASSWORD = "xmldsig.keystore.key.password";

  private static final Logger s_aLogger = LoggerFactory.getLogger (SignatureFilter.class);

  private KeyStore.PrivateKeyEntry m_aKeyEntry;
  private X509Certificate m_aCert;

  private static ConfigFile configFile;

  static {
      /* TODO : This is a quick and dirty hack to allow the use of a configuration file with an other name if it's
        in the classpath (like smp.config.properties or sml.config.properties).
        If the configuration file defined in applicationContext.xml couldn't be found, then the config.properties inside the war is used as a fallback.
        Needs to be properly refactored */
      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml"});
      configFile = (ConfigFile) context.getBean("configFile");
  }

  public SignatureFilter () {
    // Load the KeyStore and get the signing key and certificate.
    try {
      final String sKeyStoreClassPath = configFile.getString (CONFIG_XMLDSIG_KEYSTORE_CLASSPATH);
      final String sKeyStorePassword = configFile.getString (CONFIG_XMLDSIG_KEYSTORE_PASSWORD);
      final String sKeyStoreKeyAlias = configFile.getString (CONFIG_XMLDSIG_KEYSTORE_KEY_ALIAS);
      final char [] aKeyStoreKeyPassword = configFile.getCharArray (CONFIG_XMLDSIG_KEYSTORE_KEY_PASSWORD);

      final KeyStore aKeyStore = KeyStoreUtils.loadKeyStore (sKeyStoreClassPath, sKeyStorePassword);
      final KeyStore.Entry aEntry = aKeyStore.getEntry (sKeyStoreKeyAlias,
                                                        new KeyStore.PasswordProtection (aKeyStoreKeyPassword));
      if (aEntry == null) {
        // Alias not found
        throw new IllegalStateException ("Failed to find key store alias '" +
                                         sKeyStoreKeyAlias +
                                         "' in keystore '" +
                                         sKeyStorePassword +
                                         "'. Does the alias exist? Is the password correct?");
      }
      if (!(aEntry instanceof KeyStore.PrivateKeyEntry)) {
        // Not a private key
        throw new IllegalStateException ("The keystore alias '" +
                                         sKeyStoreKeyAlias +
                                         "' was found in keystore '" +
                                         sKeyStorePassword +
                                         "' but it is not a private key! The internal type is " +
                                         aEntry.getClass ().getName ());
      }
      m_aKeyEntry = (KeyStore.PrivateKeyEntry) aEntry;
      m_aCert = (X509Certificate) m_aKeyEntry.getCertificate ();
      s_aLogger.info ("Signature filter initialized with keystore '" +
                      sKeyStoreClassPath +
                      "' and alias '" +
                      sKeyStoreKeyAlias +
                      "'");

      if (false) {
        // Enable XMLDsig debugging
        java.util.logging.LogManager.getLogManager ()
                                    .readConfiguration (new StringInputStream ("handlers=java.util.logging.ConsoleHandler\r\n"
                                                                                   + ".level=FINEST\r\n"
                                                                                   + "java.util.logging.ConsoleHandler.level=FINEST\r\n"
                                                                                   + "java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter",
                                                                               CCharset.CHARSET_ISO_8859_1_OBJ));
        java.util.logging.Logger.getLogger ("org.jcp.xml.dsig.internal.level").setLevel (java.util.logging.Level.FINER);
        java.util.logging.Logger.getLogger ("org.apache.xml.internal.security.level")
                                .setLevel (java.util.logging.Level.FINER);
        java.util.logging.Logger.getLogger ("com.sun.org.apache.xml.internal.security.level")
                                .setLevel (java.util.logging.Level.FINER);
      }
    }
    catch (final Throwable t) {
      s_aLogger.error ("Error in constructor of SignatureFilter", t);
      throw new InitializationException ("Error in constructor of SignatureFilter", t);
    }
  }

  @Nonnull
  public void filter(ContainerRequestContext aRequest, ContainerResponseContext aResponse) throws IOException {
    // Make sure that the signature is only added to GET/OK on service metadata.
    if (aRequest.getMethod ().equals ("GET") && aResponse.getStatus () == Status.OK.getStatusCode ()) {
      final String sRequestPath = aRequest.getUriInfo().getPath (false);
      // Only handle requests that contain "/services/" but don't end with it
      if (sRequestPath.contains ("/services/") && !sRequestPath.endsWith ("/services/")) {
        if (s_aLogger.isDebugEnabled ()) {
          s_aLogger.debug("Will sign response to " + sRequestPath);
        }
        if(aResponse instanceof ContainerResponse) {
          ContainerRequest r = ((ContainerResponse)aResponse).getRequestContext();
          SigningContainerResponseWriter responseWriter = new SigningContainerResponseWriter(r.getResponseWriter(), m_aKeyEntry, m_aCert);
          r.setWriter(responseWriter);
        } else {
          throw new IllegalStateException("Wrong Jersey REQUEST class - development issue");
        }
      }
    }
  }

}