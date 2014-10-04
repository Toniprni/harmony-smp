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
package eu.europa.ec.cipa.commons.cenbii2.profiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.GlobalDebug;
import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotations.Nonempty;
import com.helger.commons.annotations.ReturnsImmutableObject;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.collections.ContainerHelper;
import com.helger.commons.name.IHasDisplayText;

/**
 * Defines the predefined profiles. Each profile consists of a set of
 * transactions ({@link ETransaction}) and belongs to a group {@link EGroup}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public enum EProfile implements IHasDisplayText {
  BII_MLR (EProfileName.BII_MLR, 0, ETransaction.T71),
  BII01 (EProfileName.BII01, 1, ETransaction.T19, ETransaction.T58),
  BII02 (EProfileName.BII02, 2, ETransaction.T20, ETransaction.T21, ETransaction.T59),
  BII10 (EProfileName.BII10, 10, ETransaction.T64A, ETransaction.T64B, ETransaction.T64C, ETransaction.T65),
  BII11 (EProfileName.BII11, 11, ETransaction.T41, ETransaction.T42),
  BII12 (EProfileName.BII12, 12, ETransaction.T44, ETransaction.T45),
  BII16 (EProfileName.BII16, 16, ETransaction.T22, ETransaction.T23),
  BII22 (EProfileName.BII22, 22, ETransaction.T40),
  BII34 (EProfileName.BII34, 34, ETransaction.T40, ETransaction.T69),
  BII35 (EProfileName.BII35, 35, ETransaction.T44, ETransaction.T68, ETransaction.T45);

  private final IHasDisplayText m_aName;
  private final int m_nNumber;
  private final List <ETransaction> m_aTransactions;

  private void _checkTransactionsSameGroup () {
    final EGroup eFirstGroup = m_aTransactions.get (0).getGroup ();
    final int nMax = m_aTransactions.size ();
    for (int i = 1; i < nMax; i++)
      if (m_aTransactions.get (i).getGroup () != eFirstGroup)
        throw new IllegalStateException ("Different groups in transactions for " + toString ());
  }

  private EProfile (@Nonnull final EProfileName eName,
                    @Nonnegative final int nNumber,
                    @Nonnull @Nonempty final ETransaction... aTransactions) {
    m_aName = eName;
    m_nNumber = nNumber;
    m_aTransactions = ContainerHelper.newUnmodifiableList (aTransactions);
    if (GlobalDebug.isDebugMode ())
      _checkTransactionsSameGroup ();
  }

  /**
   * @return The display name of this profile in the specified locale. Currently
   *         only English is supported.
   */
  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale) {
    return m_aName.getDisplayText (aContentLocale);
  }

  /**
   * @return The numeric value of this profile (e.g. BII04 returns 4, BII22
   *         returns 22 etc.)
   */
  @Nonnegative
  public int getNumber () {
    return m_nNumber;
  }

  /**
   * @return A non-<code>null</code> non empty list of all transactions
   *         contained in this profile.
   */
  @Nonnull
  @Nonempty
  @ReturnsImmutableObject
  public List <ETransaction> getAllTransactions () {
    return m_aTransactions;
  }

  /**
   * Check if the passed transaction is contained in this profile.
   *
   * @param eTransaction
   *        The transaction to query. May be <code>null</code>.
   * @return <code>true</code> if the passed transaction is contained in this
   *         profile, <code>false</code> otherwise.
   */
  public boolean containsTransaction (@Nullable final ETransaction eTransaction) {
    return m_aTransactions.contains (eTransaction);
  }

  /**
   * Get a list with all profiles supporting a certain transaction.
   *
   * @param eTransaction
   *        The transaction to be searched. May not be <code>null</code>.
   * @return A non-<code>null</code> non-empty list with all transactions. It
   *         may never be empty, because each transaction must occur in at least
   *         one transaction.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static List <EProfile> getAllProfilesWithTransaction (@Nonnull final ETransaction eTransaction) {
    ValueEnforcer.notNull (eTransaction, "Transaction");

    final List <EProfile> ret = new ArrayList <EProfile> ();
    for (final EProfile eProfile : values ())
      if (eProfile.containsTransaction (eTransaction))
        ret.add (eProfile);
    return ret;
  }
}
