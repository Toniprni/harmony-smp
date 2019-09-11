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

package eu.europa.ec.smp.api;

import eu.europa.ec.smp.api.exceptions.MalformedIdentifierException;
import org.apache.commons.lang3.StringUtils;
import org.oasis_open.docs.bdxr.ns.smp._2016._05.DocumentIdentifier;
import org.oasis_open.docs.bdxr.ns.smp._2016._05.ParticipantIdentifierType;
import org.oasis_open.docs.bdxr.ns.smp._2016._05.ProcessIdentifier;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Factory and utility methods for API classes generated from OASIS XSD.
 * <p>
 * Created by gutowpa on 12/01/2017.
 */
public class Identifiers {

    public static final String EBCORE_IDENTIFIER_PREFIX = "urn:oasis:names:tc:ebcore:partyid-type:";
    public static final String EBCORE_IDENTIFIER_FORMAT="%s:%s";
    public static final String EBCORE_IDENTIFIER_ISO6523_SCHEME="iso6523";
    public static final String DOUBLE_COLON_IDENTIFIER_FORMAT="%s::%s";

    private static final String EMPTY_IDENTIFIER="Null/Empty";



    public static ParticipantIdentifierType asParticipantId(String participantIDentifier) {
        String[] res = splitParticipantIdentifier(participantIDentifier);
        return new ParticipantIdentifierType(res[1], res[0]);
    }

    public static DocumentIdentifier asDocumentId(String doubleColonDelimitedId) {
        String[] res = splitDoubleColonIdentifier(doubleColonDelimitedId);
        return new DocumentIdentifier(res[1], res[0]);
    }

    public static ProcessIdentifier asProcessId(String doubleColonDelimitedId) {
        String[] res = splitDoubleColonIdentifier(doubleColonDelimitedId);
        return new ProcessIdentifier(res[1], res[0]);
    }

    public static String asString(ParticipantIdentifierType participantId) {
        String format = StringUtils.isBlank(participantId.getScheme()) &&
                StringUtils.startsWithIgnoreCase(participantId.getScheme(), EBCORE_IDENTIFIER_PREFIX)?
                EBCORE_IDENTIFIER_FORMAT:DOUBLE_COLON_IDENTIFIER_FORMAT;

        return  String.format(format, participantId.getScheme(), participantId.getValue());
    }

    public static String asString(DocumentIdentifier docId) {
        return String.format(DOUBLE_COLON_IDENTIFIER_FORMAT, docId.getScheme()!=null?docId.getScheme():"", docId.getValue());
    }

    public static String asUrlEncodedString(ParticipantIdentifierType participantId) {
        return urlEncode(asString(participantId));
    }

    public static String asUrlEncodedString(DocumentIdentifier docId) {
        return urlEncode(asString(docId));
    }

    private static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
    private static String[] splitParticipantIdentifier(String participantIdentifier) {

        String[] idResult;
        if (StringUtils.isBlank(participantIdentifier)){
            throw new MalformedIdentifierException(EMPTY_IDENTIFIER, null);
        }
        String identifier = participantIdentifier.trim();
        if(identifier.startsWith(EBCORE_IDENTIFIER_PREFIX)
                || identifier.startsWith("::" + EBCORE_IDENTIFIER_PREFIX)) {
            idResult = splitEbCoreIdentifier(identifier);
        } else {
            idResult = splitDoubleColonIdentifier(identifier);
        }

        return idResult;

    }

    /**
     * Method splits identifier at first occurrence of double colon :: and returns array size of 2. The first value is
     * schema and the second is identifier. If identifier is blank or with missing :: MalformedIdentifierException is thrown
     * @param doubleColonDelimitedId
     * @return array with two elements. First is schema and second is id
     */

    private static String[] splitDoubleColonIdentifier(String doubleColonDelimitedId) {
        if (StringUtils.isBlank(doubleColonDelimitedId)){
            throw new MalformedIdentifierException(EMPTY_IDENTIFIER, null);
        }

        String[] idResult = new String[2];

        int delimiterIndex = doubleColonDelimitedId.indexOf("::");
        if (delimiterIndex<0){
            throw new MalformedIdentifierException(doubleColonDelimitedId, null);
        }
        idResult[0] = delimiterIndex==0?null:doubleColonDelimitedId.substring(0,delimiterIndex);
        idResult[1] = doubleColonDelimitedId.substring(delimiterIndex+2);

        if (StringUtils.isBlank(idResult[1])){
            throw new MalformedIdentifierException(doubleColonDelimitedId, null);
        }

        return idResult;

    }

    public static String[] splitEbCoreIdentifier(final String partyId)  {

        String partyIdPrivate = partyId.trim();
        if (partyIdPrivate.startsWith("::")) {
            partyIdPrivate = StringUtils.removeStart(partyIdPrivate, "::");
        }

        if (!partyIdPrivate.startsWith(EBCORE_IDENTIFIER_PREFIX)){
            throw new MalformedIdentifierException(partyId, null);
        }
        boolean isIso6523 = partyIdPrivate.startsWith(EBCORE_IDENTIFIER_PREFIX+EBCORE_IDENTIFIER_ISO6523_SCHEME +":");

        int isSchemeDelimiter = partyIdPrivate.indexOf(':',EBCORE_IDENTIFIER_PREFIX.length());
        if (isSchemeDelimiter < 0){
            // invalid scheme
            throw new IllegalArgumentException(String.format("Invalid ebCore id [%s] ebcoreId must have prefix 'urn:oasis:names:tc:ebcore:partyid-type', "+
                    "and parts <catalog-identifier>, <scheme-in-catalog>, <scheme-specific-identifier> separated by colon.  " +
                    "Example: urn:oasis:names:tc:ebcore:partyid-type:<catalog-identifier>:(<scheme-in-catalog>)?:<scheme-specific-identifier>.", partyIdPrivate));
        }
        int isPartDelimiter = partyIdPrivate.indexOf(':',isSchemeDelimiter+1);

        String[] result = new String[2];
        if (isPartDelimiter < 0 && isIso6523) { // for iso scheme-in-catalog is mandatory
            // invalid scheme
            throw new IllegalArgumentException(String.format("Invalid ebCore id [%s] ebcoreId must have prefix 'urn:oasis:names:tc:ebcore:partyid-type', " +
                    "and parts <catalog-identifier>, <scheme-in-catalog>, <scheme-specific-identifier> separated by colon.  " +
                    "Example: urn:oasis:names:tc:ebcore:partyid-type:<catalog-identifier>:(<scheme-in-catalog>)?:<scheme-specific-identifier>.", partyIdPrivate));
        } else if (isPartDelimiter < 0){
            result[0] = partyIdPrivate.substring(0, isSchemeDelimiter).trim();
            result[1] = partyIdPrivate.substring(isSchemeDelimiter + 1).trim();
        }else {
            result[0] = partyIdPrivate.substring(0, isPartDelimiter).trim();
            result[1] = partyIdPrivate.substring(isPartDelimiter + 1).trim();
        }

        //check if double colon was used for identifier separator in ebecoreid
        if (result[1].startsWith(":")) {
            result[1] = StringUtils.removeStart(result[1], ":");
        }
        return result;

    }


}
