
package eu.europa.ec.cipa.peppol.identifier.process;

import java.util.List;
import javax.annotation.Nonnull;
import com.helger.commons.annotations.Nonempty;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.collections.CollectionHelper;
import com.helger.commons.version.Version;
import eu.europa.ec.cipa.peppol.identifier.CIdentifier;
import eu.europa.ec.cipa.peppol.identifier.IdentifierUtils;
import eu.europa.ec.cipa.peppol.identifier.doctype.EPredefinedDocumentTypeIdentifier;
import eu.europa.ec.cipa.peppol.identifier.doctype.IPeppolPredefinedDocumentTypeIdentifier;


/**
 * This file is generated. Do NOT edit!
 * 
 */
public enum EPredefinedProcessIdentifier
    implements IPeppolPredefinedProcessIdentifier
{

    /**
     * urn:www.cenbii.eu:profile:bii01:ver1.0
     * @since code list 1.0.0
     * 
     */
    urn_www_cenbii_eu_profile_bii01_ver1_0("urn:www.cenbii.eu:profile:bii01:ver1.0", "urn:www.peppol.eu:bis:peppol1a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.CATALOGUE_T019_BIS1A, EPredefinedDocumentTypeIdentifier.APPLICATIONRESPONSE_T057_BIS1A, EPredefinedDocumentTypeIdentifier.APPLICATIONRESPONSE_T058_BIS1A }, new Version("1.0.0")),

    /**
     * urn:www.cenbii.eu:profile:bii01:ver2.0
     * @since code list 1.2.0
     * 
     */
    urn_www_cenbii_eu_profile_bii01_ver2_0("urn:www.cenbii.eu:profile:bii01:ver2.0", "urn:www.peppol.eu:bis:peppol1a:ver4.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.CATALOGUE_T019_BIS1A_V40 }, new Version("1.2.0")),

    /**
     * urn:www.cenbii.eu:profile:bii03:ver1.0
     * @since code list 1.0.0
     * 
     */
    urn_www_cenbii_eu_profile_bii03_ver1_0("urn:www.cenbii.eu:profile:bii03:ver1.0", "urn:www.peppol.eu:bis:peppol3a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.ORDER_T001_BIS3A }, new Version("1.0.0")),

    /**
     * urn:www.cenbii.eu:profile:bii03:ver2.0
     * @since code list 1.2.0
     * 
     */
    urn_www_cenbii_eu_profile_bii03_ver2_0("urn:www.cenbii.eu:profile:bii03:ver2.0", "urn:www.peppol.eu:bis:peppol03a:ver2.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.ORDER_T001_BIS03A_V20 }, new Version("1.2.0")),

    /**
     * urn:www.cenbii.eu:profile:bii04:ver1.0
     * @since code list 1.0.0
     * 
     */
    urn_www_cenbii_eu_profile_bii04_ver1_0("urn:www.cenbii.eu:profile:bii04:ver1.0", "urn:www.peppol.eu:bis:peppol4a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A }, new Version("1.0.0")),

    /**
     * urn:www.cenbii.eu:profile:bii04:ver2.0
     * @since code list 1.2.0
     * 
     */
    urn_www_cenbii_eu_profile_bii04_ver2_0("urn:www.cenbii.eu:profile:bii04:ver2.0", "urn:www.peppol.eu:bis:peppol4a:ver2.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20 }, new Version("1.2.0")),

    /**
     * urn:www.cenbii.eu:profile:bii05:ver1.0
     * @since code list 1.1.0
     * 
     */
    urn_www_cenbii_eu_profile_bii05_ver1_0("urn:www.cenbii.eu:profile:bii05:ver1.0", "urn:www.peppol.eu:bis:peppol5a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS5A, EPredefinedDocumentTypeIdentifier.CREDITNOTE_T014_BIS5A, EPredefinedDocumentTypeIdentifier.INVOICE_T015_BIS5A }, new Version("1.1.0")),

    /**
     * urn:www.cenbii.eu:profile:bii05:ver2.0
     * @since code list 1.2.0
     * 
     */
    urn_www_cenbii_eu_profile_bii05_ver2_0("urn:www.cenbii.eu:profile:bii05:ver2.0", "urn:www.peppol.eu:bis:peppol5a:ver2.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS5A_V20, EPredefinedDocumentTypeIdentifier.CREDITNOTE_T014_BIS5A_V20 }, new Version("1.2.0")),

    /**
     * urn:www.cenbii.eu:profile:bii06:ver1.0
     * @since code list 1.0.0
     * 
     */
    urn_www_cenbii_eu_profile_bii06_ver1_0("urn:www.cenbii.eu:profile:bii06:ver1.0", "urn:www.peppol.eu:bis:peppol6a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.ORDER_T001_BIS6A, EPredefinedDocumentTypeIdentifier.ORDERRESPONSESIMPLE_T002_BIS6A, EPredefinedDocumentTypeIdentifier.ORDERRESPONSESIMPLE_T003_BIS6A, EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS6A, EPredefinedDocumentTypeIdentifier.CREDITNOTE_T014_BIS6A, EPredefinedDocumentTypeIdentifier.INVOICE_T015_BIS6A }, new Version("1.0.0")),

    /**
     * urn:www.cenbii.eu:profile:bii28:ver2.0
     * @since code list 1.2.0
     * 
     */
    urn_www_cenbii_eu_profile_bii28_ver2_0("urn:www.cenbii.eu:profile:bii28:ver2.0", "urn:www.peppol.eu:bis:peppol28a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.ORDER_T001_BIS28A, EPredefinedDocumentTypeIdentifier.ORDER_T076_BIS28A }, new Version("1.2.0")),

    /**
     * urn:www.cenbii.eu:profile:bii30:ver2.0
     * @since code list 1.2.0
     * 
     */
    urn_www_cenbii_eu_profile_bii30_ver2_0("urn:www.cenbii.eu:profile:bii30:ver2.0", "urn:www.peppol.eu:bis:peppol30a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.DESPATCHADVICE_T016_BIS30A }, new Version("1.2.0")),

    /**
     * urn:www.cenbii.eu:profile:bii36:ver2.0
     * @since code list 1.2.0
     * 
     */
    urn_www_cenbii_eu_profile_bii36_ver2_0("urn:www.cenbii.eu:profile:bii36:ver2.0", "urn:www.peppol.eu:bis:peppol36a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.APPLICATIONRESPONSE_T071_BIS36A }, new Version("1.2.0"));
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii01_ver1_0}
     * 
     */
    public final static EPredefinedProcessIdentifier BIS1A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii01_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii01_ver2_0}
     * 
     */
    public final static EPredefinedProcessIdentifier BIS1A_V40 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii01_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii03_ver1_0}
     * 
     */
    public final static EPredefinedProcessIdentifier BIS3A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii03_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii03_ver2_0}
     * 
     */
    public final static EPredefinedProcessIdentifier BIS03A_V20 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii03_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii04_ver1_0}
     * 
     */
    public final static EPredefinedProcessIdentifier BIS4A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii04_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii04_ver2_0}
     * 
     */
    public final static EPredefinedProcessIdentifier BIS4A_V20 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii04_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii05_ver1_0}
     * 
     */
    public final static EPredefinedProcessIdentifier BIS5A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii05_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii05_ver2_0}
     * 
     */
    public final static EPredefinedProcessIdentifier BIS5A_V20 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii05_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii06_ver1_0}
     * 
     */
    public final static EPredefinedProcessIdentifier BIS6A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii06_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii28_ver2_0}
     * 
     */
    public final static EPredefinedProcessIdentifier BIS28A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii28_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii30_ver2_0}
     * 
     */
    public final static EPredefinedProcessIdentifier BIS30A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii30_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii36_ver2_0}
     * 
     */
    public final static EPredefinedProcessIdentifier BIS36A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii36_ver2_0;
    private final String m_sID;
    private final String m_sBISID;
    private final EPredefinedDocumentTypeIdentifier[] m_aDocIDs;
    private final Version m_aSince;

    private EPredefinedProcessIdentifier(
        @Nonnull
        @Nonempty
        final String sID,
        @Nonnull
        @Nonempty
        final String sBISID,
        @Nonnull
        @Nonempty
        final EPredefinedDocumentTypeIdentifier[] aDocIDs,
        @Nonnull
        final Version aSince) {
        m_sID = sID;
        m_sBISID = sBISID;
        m_aDocIDs = aDocIDs;
        m_aSince = aSince;
    }

    @Nonnull
    @Nonempty
    public String getScheme() {
        return CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME;
    }

    @Nonnull
    @Nonempty
    public String getValue() {
        return m_sID;
    }

    @Nonnull
    @Nonempty
    public String getBISID() {
        return m_sBISID;
    }

    @Nonnull
    @ReturnsMutableCopy
    public List<? extends IPeppolPredefinedDocumentTypeIdentifier> getDocumentTypeIdentifiers() {
        return CollectionHelper.newList(m_aDocIDs);
    }

    @Nonnull
    public SimpleProcessIdentifier getAsProcessIdentifier() {
        return new SimpleProcessIdentifier(this);
    }

    @Nonnull
    public Version getSince() {
        return m_aSince;
    }

    @Nonnull
    public boolean isDefaultScheme() {
        return true;
    }

    @Nonnull
    public String getURIEncoded() {
        return IdentifierUtils.getIdentifierURIEncoded(this);
    }

    @Nonnull
    public String getURIPercentEncoded() {
        return IdentifierUtils.getIdentifierURIPercentEncoded(this);
    }
}
