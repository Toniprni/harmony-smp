/*
 * Copyright 2017 European Commission | CEF eDelivery
 *
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence attached in file: LICENCE-EUPL-v1.2.pdf
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package eu.europa.ec.edelivery.smp.data.model;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "smp_service_metadata")
@Audited
public class DBServiceMetadata implements BaseEntity {

    private DBServiceMetadataId serviceMetadataId;
    private DBServiceGroup serviceGroup;
    private String xmlContent;

    public DBServiceMetadata() {  }

    public DBServiceMetadata(DBServiceMetadataId serviceMetadataId, DBServiceGroup serviceGroup) {
        this(serviceMetadataId, serviceGroup, null);
    }

    public DBServiceMetadata(DBServiceMetadataId serviceMetadataId,
                             DBServiceGroup serviceGroup,
                             String xmlContent) {
        this.serviceMetadataId = serviceMetadataId;
        this.serviceGroup = serviceGroup;
        this.xmlContent = xmlContent;
    }

    @EmbeddedId
    @Override
    public DBServiceMetadataId getId() {
        return serviceMetadataId;
    }

    @ManyToOne(fetch = EAGER)
    @JoinColumns({@JoinColumn(name = "businessIdentifier",
            referencedColumnName = "businessIdentifier",
            nullable = false,
            insertable = false,
            updatable = false),
            @JoinColumn(name = "businessIdentifierScheme",
                    referencedColumnName = "businessIdentifierScheme",
                    nullable = false,
                    insertable = false,
                    updatable = false)})
    public DBServiceGroup getServiceGroup() {
        return serviceGroup;
    }

    @Lob
    @Column(name = "xmlcontent")
    public String getXmlContent() {
        return xmlContent;
    }

    public void setId(final DBServiceMetadataId serviceMetadataId) {
        this.serviceMetadataId = serviceMetadataId;
    }

    public void setServiceGroup(final DBServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    public void setXmlContent(String xmlContent) {
        this.xmlContent = xmlContent;
    }

}
