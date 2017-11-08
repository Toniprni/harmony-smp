/*
 * Copyright 2017 European Commission | CEF eDelivery
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/software/page/eupl
 * or file: LICENCE-EUPL-v1.1.pdf
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

/*
 * Copyright 2017 European Commission | CEF eDelivery
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/software/page/eupl
 * or file: LICENCE-EUPL-v1.1.pdf
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package eu.europa.ec.edelivery.smp.validation;

import eu.europa.ec.edelivery.smp.error.exceptions.BadRequestException;
import eu.europa.ec.cipa.smp.server.util.ConfigFile;
import eu.europa.ec.cipa.smp.server.util.IdentifierUtils;
import eu.europa.ec.smp.api.Identifiers;
import org.oasis_open.docs.bdxr.ns.smp._2016._05.ParticipantIdentifierType;
import org.oasis_open.docs.bdxr.ns.smp._2016._05.ServiceGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static eu.europa.ec.edelivery.smp.error.ErrorBusinessCode.WRONG_FIELD;


/**
 * Created by gutowpa on 02/08/2017.
 */
@Component
public class ServiceGroupValidator {

    private static final String CONFIG_SERVICE_GROUP_SCHEME_REGEXP = "identifiersBehaviour.ParticipantIdentifierScheme.validationRegex";

    private Pattern schemaPattern;

    @Autowired
    ConfigFile configFile;

    @PostConstruct
    public void init() {
        String regex = configFile.getString(CONFIG_SERVICE_GROUP_SCHEME_REGEXP);
        try {
            schemaPattern = Pattern.compile(regex);
        } catch (PatternSyntaxException | NullPointerException e) {
            throw new IllegalStateException("Contact Administrator. ServiceGroup schema pattern is wrongly configured: " + regex);
        }

    }

    public void validate(String serviceGroupId, ServiceGroup serviceGroup) {

        final ParticipantIdentifierType aServiceGroupID = Identifiers.asParticipantId(serviceGroupId);
        if (!IdentifierUtils.areIdentifiersEqual(aServiceGroupID, serviceGroup.getParticipantIdentifier())) {
            // Business identifier must equal path
            throw new BadRequestException(WRONG_FIELD, "Service Group Ids don't match between URL parameter and XML body");
        }

        String scheme = serviceGroup.getParticipantIdentifier().getScheme();
        if (!schemaPattern.matcher(scheme).matches()) {
            throw new BadRequestException(WRONG_FIELD, "Service Group scheme does not match allowed pattern: " + schemaPattern.pattern());
        }
    }

}
