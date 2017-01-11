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
package eu.europa.ec.cipa.smp.server.data.dbms.model;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.helger.commons.annotations.UsedViaReflection;
import com.helger.commons.equals.EqualsUtils;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

import eu.europa.ec.cipa.busdox.identifier.IReadonlyDocumentTypeIdentifier;
import eu.europa.ec.cipa.busdox.identifier.IReadonlyProcessIdentifier;
import eu.europa.ec.cipa.peppol.identifier.CIdentifier;
import eu.europa.ec.cipa.peppol.identifier.IdentifierUtils;
import org.oasis_open.docs.bdxr.ns.smp._2016._05.DocumentIdentifier;
import org.oasis_open.docs.bdxr.ns.smp._2016._05.ParticipantIdentifierType;
import org.oasis_open.docs.bdxr.ns.smp._2016._05.ProcessIdentifier;

/**
 * EndpointId generated by hbm2java
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Embeddable
public class DBEndpointID implements Serializable {
  private String m_sParticipantIdentifierScheme;
  private String m_sParticipantIdentifier;
  private String m_sDocumentTypeIdentifierScheme;
  private String m_sDocumentTypeIdentifier;
  private String m_sProcessIdentifierScheme;
  private String m_sProcessIdentifier;
  private String m_sEndpointReference;
  private String m_sTransportProfile;

  @Deprecated
  @UsedViaReflection
  public DBEndpointID () {}

  public DBEndpointID (@Nonnull final DBProcessID aProcessID,
                       final String sEndpointReference,
                       final String sTransportProfile) {
    setBusinessIdentifier (aProcessID.asBusinessIdentifier ());
    setDocumentTypeIdentifier (aProcessID.asDocumentTypeIdentifier ());
    setProcessIdentifier (aProcessID.asProcessIdentifier ());
    setEndpointReference (sEndpointReference);
    setTransportProfile (sTransportProfile);
  }

  @Column (name = "businessIdentifierScheme", nullable = false, length = CIdentifier.MAX_IDENTIFIER_SCHEME_LENGTH)
  public String getBusinessIdentifierScheme () {
    return m_sParticipantIdentifierScheme;
  }

  public void setBusinessIdentifierScheme (final String sBusinessIdentifierScheme) {
    m_sParticipantIdentifierScheme = IdentifierUtils.getUnifiedParticipantDBValue (sBusinessIdentifierScheme);
  }

  @Column (name = "businessIdentifier", nullable = false, length = CIdentifier.MAX_PARTICIPANT_IDENTIFIER_VALUE_LENGTH)
  public String getBusinessIdentifier () {
    return m_sParticipantIdentifier;
  }

  public void setBusinessIdentifier (final String sBusinessIdentifier) {
    m_sParticipantIdentifier = IdentifierUtils.getUnifiedParticipantDBValue (sBusinessIdentifier);
  }

  @Transient
  public void setBusinessIdentifier (@Nonnull final ParticipantIdentifierType aBusinessIdentifier) {
    setBusinessIdentifierScheme (aBusinessIdentifier.getScheme ());
    setBusinessIdentifier (aBusinessIdentifier.getValue ());
  }

  @Column (name = "processIdentifierType", nullable = false, length = CIdentifier.MAX_IDENTIFIER_SCHEME_LENGTH)
  public String getProcessIdentifierScheme () {
    return m_sProcessIdentifierScheme;
  }

  public void setProcessIdentifierScheme (final String sProcessIdentifierScheme) {
    m_sProcessIdentifierScheme = sProcessIdentifierScheme;
  }

  @Column (name = "processIdentifier", nullable = false, length = CIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH)
  public String getProcessIdentifier () {
    return m_sProcessIdentifier;
  }

  public void setProcessIdentifier (final String sProcessIdentifier) {
    m_sProcessIdentifier = sProcessIdentifier;
  }

  @Transient
  public void setProcessIdentifier (@Nonnull final ProcessIdentifier aProcessID) {
    setProcessIdentifierScheme (aProcessID.getScheme ());
    setProcessIdentifier (aProcessID.getValue ());
  }

  @Column (name = "documentIdentifierScheme", nullable = false, length = CIdentifier.MAX_IDENTIFIER_SCHEME_LENGTH)
  public String getDocumentIdentifierScheme () {
    return m_sDocumentTypeIdentifierScheme;
  }

  public void setDocumentIdentifierScheme (final String sDocumentIdentifierScheme) {
    m_sDocumentTypeIdentifierScheme = sDocumentIdentifierScheme;
  }

  @Column (name = "documentIdentifier", nullable = false, length = CIdentifier.MAX_DOCUMENT_TYPE_IDENTIFIER_VALUE_LENGTH)
  public String getDocumentIdentifier () {
    return m_sDocumentTypeIdentifier;
  }

  public void setDocumentIdentifier (final String sDocumentIdentifier) {
    m_sDocumentTypeIdentifier = sDocumentIdentifier;
  }

  @Transient
  public void setDocumentTypeIdentifier (@Nonnull final DocumentIdentifier aDocumentTypeID) {
    setDocumentIdentifierScheme (aDocumentTypeID.getScheme ());
    setDocumentIdentifier (aDocumentTypeID.getValue ());
  }

  @Column (name = "endpointReference", nullable = false, length = 256)
  public String getEndpointReference () {
    return m_sEndpointReference;
  }

  public void setEndpointReference (final String sEndpointReference) {
    m_sEndpointReference = sEndpointReference;
  }

  @Column (name = "transportProfile", nullable = false, length = 256)
  public String getTransportProfile () {
    return m_sTransportProfile;
  }

  public void setTransportProfile (final String sTransportProfile) {
    m_sTransportProfile = sTransportProfile;
  }

  @Override
  public boolean equals (final Object o) {
    if (this == o)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final DBEndpointID rhs = (DBEndpointID) o;
    return EqualsUtils.equals (m_sParticipantIdentifierScheme, rhs.m_sParticipantIdentifierScheme) &&
           EqualsUtils.equals (m_sParticipantIdentifier, rhs.m_sParticipantIdentifier) &&
           EqualsUtils.equals (m_sDocumentTypeIdentifierScheme, rhs.m_sDocumentTypeIdentifierScheme) &&
           EqualsUtils.equals (m_sDocumentTypeIdentifier, rhs.m_sDocumentTypeIdentifier) &&
           EqualsUtils.equals (m_sProcessIdentifierScheme, rhs.m_sProcessIdentifierScheme) &&
           EqualsUtils.equals (m_sProcessIdentifier, rhs.m_sProcessIdentifier) &&
           EqualsUtils.equals (m_sEndpointReference, rhs.m_sEndpointReference) &&
           EqualsUtils.equals (m_sTransportProfile, rhs.m_sTransportProfile);
  }

  @Override
  public int hashCode () {
    return new HashCodeGenerator (this).append (m_sParticipantIdentifierScheme)
                                       .append (m_sParticipantIdentifier)
                                       .append (m_sDocumentTypeIdentifierScheme)
                                       .append (m_sDocumentTypeIdentifier)
                                       .append (m_sProcessIdentifierScheme)
                                       .append (m_sProcessIdentifier)
                                       .append (m_sEndpointReference)
                                       .append (m_sTransportProfile)
                                       .getHashCode ();
  }

  @Override
  public String toString () {
    return new ToStringGenerator (this).append ("participantIDScheme", m_sParticipantIdentifierScheme)
                                       .append ("participantIDValue", m_sParticipantIdentifier)
                                       .append ("documentTypeIDScheme", m_sDocumentTypeIdentifierScheme)
                                       .append ("documentTypeIDValue", m_sDocumentTypeIdentifier)
                                       .append ("processIDScheme", m_sProcessIdentifierScheme)
                                       .append ("processIDValue", m_sProcessIdentifier)
                                       .append ("endpointReference", m_sEndpointReference)
                                       .append ("transportProfile", m_sTransportProfile)
                                       .toString ();
  }
}
