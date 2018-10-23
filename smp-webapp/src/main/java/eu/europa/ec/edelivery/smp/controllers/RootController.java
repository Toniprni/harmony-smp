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

package eu.europa.ec.edelivery.smp.controllers;

import eu.europa.ec.edelivery.smp.logging.SMPLogger;
import eu.europa.ec.edelivery.smp.logging.SMPLoggerFactory;
import eu.europa.ec.edelivery.smp.logging.SMPMessageCode;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.helpers.IOUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;


@RestController
@RequestMapping("/")
public class RootController {

    private static final SMPLogger LOG = SMPLoggerFactory.getLogger(RootController.class);


    @GetMapping( produces = MediaType.TEXT_HTML_VALUE, value={"", "/", "web/index.html","index.html"})
    @Order(HIGHEST_PRECEDENCE)
    public byte[] getServiceGroup(HttpServletRequest httpReq) throws IOException {
        String host = httpReq.getRemoteHost();
        LOG.businessInfo(SMPMessageCode.BUS_HTTP_GET_END_STATIC_CONTENT,host,httpReq.getPathInfo());
        return IOUtils.readBytesFromStream(RootController.class.getResourceAsStream("/index.html"));
    }

    /**
     * redirect angular pages to index.html
     * solve the 404 error on refresh
     * @param model
     * @return
*/
    //@GetMapping(value={"/ui","/ui/edit","/ui/search","/ui/search","/ui/domain","/ui/user"})
    @GetMapping(value={"/ui"})
    public ModelAndView redirectWithUsingRedirectPrefix(ModelMap model) {
        return new ModelAndView("redirect:/ui/index.html", model);
    }

    public String getRemoteHost(HttpServletRequest httpReq){
        String host = httpReq.getHeader("X-Forwarded-For");
        return StringUtils.isBlank(host)?httpReq.getRemoteHost():host;
    }
}
