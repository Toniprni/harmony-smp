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
package eu.europa.ec.cipa.webgui.app.components.tables;

import java.util.List;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CommodityClassificationType;

import com.vaadin.data.Item;
import com.vaadin.data.util.NestedMethodProperty;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.FormLayout;

import eu.europa.ec.cipa.webgui.app.components.adapters.CommodityClassificationAdapter;

public class InvoiceLineCommodityClassificationTableEditor extends
                                                          GenericTableEditor <CommodityClassificationType, CommodityClassificationAdapter> {

  public InvoiceLineCommodityClassificationTableEditor (final boolean editMode) {
    super (editMode);
  }

  @Override
  public Form createTableForm (final CommodityClassificationAdapter t,
                               final List <CommodityClassificationType> invoiceList) {

    final Form invoiceLineCommodityForm = new Form (new FormLayout (), new CommodityFormFactory ());
    invoiceLineCommodityForm.setImmediate (true);

    // NestedMethodProperty mp = new NestedMethodProperty(t, "tableLineID");
    if (!editMode) {
      t.setIDAdapter (String.valueOf (invoiceList.size () + 1));
    }
    else {
      // mp.setReadOnly (true);
    }

    // invoiceItemPropertyForm.addItemProperty ("Line ID #", new
    // NestedMethodProperty(itemPropertyBean, "ID.value") );
    // invoiceItemPropertyForm.addItemProperty ("Line ID #", mp );
    invoiceLineCommodityForm.addItemProperty ("Commodity Code", new NestedMethodProperty (t, "InvLineCommodityCode"));

    return invoiceLineCommodityForm;
  }

  class CommodityFormFactory implements FormFieldFactory {

    @Override
    public Field createField (final Item item, final Object propertyId, final Component uiContext) {
      // Identify the fields by their Property ID.
      final String pid = (String) propertyId;

      final Field field = DefaultFieldFactory.get ().createField (item, propertyId, uiContext);
      if (field instanceof AbstractTextField) {
        ((AbstractTextField) field).setNullRepresentation ("");
        final AbstractTextField tf = (AbstractTextField) field;
        tf.addListener (new FieldEvents.FocusListener () {
          @Override
          public void focus (final FocusEvent event) {
            tf.selectAll ();
          }
        });
      }

      return field;
    }
  }

  @Override
  public CommodityClassificationAdapter createItem () {
    final CommodityClassificationAdapter ac = new CommodityClassificationAdapter ();

    ac.setInvLineCommodityCode ("");

    return ac;
  }

  @Override
  public void cloneItem (final CommodityClassificationAdapter srcItem, final CommodityClassificationAdapter dstItem) {

    dstItem.setIDAdapter (srcItem.getIDAdapter ());
    dstItem.setInvLineCommodityCode (srcItem.getInvLineCommodityCode ());

  }

}
