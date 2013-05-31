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
package eu.europa.ec.cipa.transport.start.client.console;

import org.apache.commons.cli.Options;

public final class StartClientOptions extends Options {

	public StartClientOptions() {
		super();
		addOption("m", "clientmode", true, "The client console mode : \n DIRECT_AP : Sends the document directly to the Access point without using sml and smp (requires ap url)" +
				  "\n DIRECT_SMP : Sends the document querying the provided smp url to get the recipient endpoint(requires ap url)" +
				  "\n FULL : Sends the document using the full transport infrastructure");
		addOption("ap", "apurl", true, "URL of the recipient access point  in case of DIRECT_AP client mode");
		addOption("smp", "smpurl", true, "URL of smp to use in case of DIRECT_SMP client mofde");
		addOption("s", "senderid", true, "Business id of the sender fi 0088:123456789");
		addOption("r", "recipientid", true, "Business id of the recipient fi  0088:123456710");
		addOption("p", "processid", true, "Process id to perform operation on ");
		addOption("d", "documenttypeId", true,"Document id to perform operation on");
		addOption("dpath", "documentPath", true,"the path pointing to the xml file to send");
		addOption("muid", "messageID", true,"Optional Message Identifier if not present uid gets generated by the ap");
		addOption("ping", "sendPingMessage",true,"Optional : set this value to true if you want to send a ping message");
		addOption("debug", "enableDebug",true,"Optional : set this value to true if you want to enable metro debug");
		addOption("proxy","useProxt",true,"Optional : set this value to true if you want to use proxy edit the configProxy.properties file under the conf folder "); 
	}

}
