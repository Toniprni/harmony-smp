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

import com.helger.commons.annotations.UsedViaReflection;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Process generated by hbm2java
 * 
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Entity
@Table (name = "smp_process")
public class DBProcess implements Serializable {


  private DBProcessID m_aID;
  private DBServiceMetadata m_aServiceMetadata;
  private String m_sExtension;
  private Set <DBEndpoint> m_aEndpoints = new HashSet <DBEndpoint> (0);

  @Deprecated
  @UsedViaReflection
  public DBProcess () {}

  public DBProcess (final DBProcessID aID) {
    m_aID = aID;
  }

  @EmbeddedId
  @Nullable
  public DBProcessID getId () {
    return m_aID;
  }

  public void setId (final DBProcessID aID) {
    m_aID = aID;
  }

  @ManyToOne (fetch = FetchType.LAZY)
  @JoinColumns ({ @JoinColumn (name = "documentIdentifier",
                               referencedColumnName = "documentIdentifier",
                               nullable = false,
                               insertable = false,
                               updatable = false),
                 @JoinColumn (name = "documentIdentifierScheme",
                              referencedColumnName = "documentIdentifierScheme",
                              nullable = false,
                              insertable = false,
                              updatable = false),
                 @JoinColumn (name = "businessIdentifier",
                              referencedColumnName = "businessIdentifier",
                              nullable = false,
                              insertable = false,
                              updatable = false),
                 @JoinColumn (name = "businessIdentifierScheme",
                              referencedColumnName = "businessIdentifierScheme",
                              nullable = false,
                              insertable = false,
                              updatable = false) })
  public DBServiceMetadata getServiceMetadata () {
    return m_aServiceMetadata;
  }

  public void setServiceMetadata (final DBServiceMetadata aServiceMetadata) {
    m_aServiceMetadata = aServiceMetadata;
  }

  @Lob
  @Column (name = "extension", length = 65535)
  public String getExtension () {
    return m_sExtension;
  }

  public void setExtension (@Nullable final String sExtension) {
    m_sExtension = sExtension;
  }

  @OneToMany (fetch = FetchType.LAZY, mappedBy = "process", cascade = { CascadeType.ALL })
  public Set <DBEndpoint> getEndpoints () {
    return m_aEndpoints;
  }

  public void setEndpoints (final Set <DBEndpoint> aEndpoints) {
    m_aEndpoints = aEndpoints;
  }
}
