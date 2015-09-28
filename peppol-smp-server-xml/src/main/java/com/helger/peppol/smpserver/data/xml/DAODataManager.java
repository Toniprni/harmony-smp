/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.peppol.smpserver.data.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.annotation.UsedViaReflection;
import com.helger.commons.annotation.VisibleForTesting;
import com.helger.peppol.identifier.DocumentIdentifierType;
import com.helger.peppol.identifier.IdentifierHelper;
import com.helger.peppol.identifier.ParticipantIdentifierType;
import com.helger.peppol.identifier.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.participant.SimpleParticipantIdentifier;
import com.helger.peppol.smp.SMPExtensionConverter;
import com.helger.peppol.smp.ServiceGroupType;
import com.helger.peppol.smp.ServiceInformationType;
import com.helger.peppol.smp.ServiceMetadataType;
import com.helger.peppol.smpserver.data.IDataManagerSPI;
import com.helger.peppol.smpserver.data.IDataUser;
import com.helger.peppol.smpserver.domain.MetaManager;
import com.helger.peppol.smpserver.domain.SMPHelper;
import com.helger.peppol.smpserver.domain.redirect.ISMPRedirect;
import com.helger.peppol.smpserver.domain.redirect.ISMPRedirectManager;
import com.helger.peppol.smpserver.domain.servicegroup.ISMPServiceGroup;
import com.helger.peppol.smpserver.domain.servicegroup.ISMPServiceGroupManager;
import com.helger.peppol.smpserver.domain.serviceinfo.ISMPServiceInformation;
import com.helger.peppol.smpserver.domain.serviceinfo.ISMPServiceInformationManager;
import com.helger.peppol.smpserver.domain.serviceinfo.SMPServiceInformation;
import com.helger.peppol.smpserver.exception.SMPNotFoundException;
import com.helger.peppol.smpserver.exception.SMPUnauthorizedException;
import com.helger.peppol.smpserver.exception.SMPUnknownUserException;
import com.helger.peppol.smpserver.smlhook.IRegistrationHook;
import com.helger.peppol.smpserver.smlhook.RegistrationHookException;
import com.helger.peppol.smpserver.smlhook.RegistrationHookFactory;
import com.helger.photon.basic.security.AccessManager;
import com.helger.photon.basic.security.user.IUser;
import com.helger.web.http.basicauth.BasicAuthClientCredentials;

/**
 * The DAO based {@link IDataManagerSPI}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class DAODataManager implements IDataManagerSPI
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (DAODataManager.class);

  private final IRegistrationHook m_aHook;

  @Deprecated
  @UsedViaReflection
  public DAODataManager ()
  {
    this (RegistrationHookFactory.getOrCreateInstance ());
  }

  @VisibleForTesting
  DAODataManager (@Nonnull final IRegistrationHook aHook)
  {
    m_aHook = ValueEnforcer.notNull (aHook, "Hook");
  }

  @Nonnull
  public DAODataUser getUserFromCredentials (@Nonnull final BasicAuthClientCredentials aCredentials) throws SMPUnauthorizedException,
                                                                                                     SMPUnknownUserException
  {
    final AccessManager aAccessMgr = AccessManager.getInstance ();
    final IUser aUser = aAccessMgr.getUserOfLoginName (aCredentials.getUserName ());
    if (aUser == null)
    {
      s_aLogger.info ("Invalid login name provided: '" + aCredentials.getUserName () + "'");
      throw new SMPUnknownUserException (aCredentials.getUserName ());
    }
    if (!aAccessMgr.areUserIDAndPasswordValid (aUser.getID (), aCredentials.getPassword ()))
    {
      s_aLogger.info ("Invalid password provided for '" + aCredentials.getUserName () + "'");
      throw new SMPUnauthorizedException ("Username and/or password are invalid!");
    }
    return new DAODataUser (aUser);
  }

  @Nonnull
  public DAODataUser createPreAuthenticatedUser (@Nonnull @Nonempty final String sUserName)
  {
    return new DAODataUser (AccessManager.getInstance ().getUserOfLoginName (sUserName));
  }

  /**
   * Verify that the passed service group is owned by the user specified in the
   * credentials.
   *
   * @param aServiceGroupID
   *        The service group to be verified
   * @param sUserLoginName
   *        The user login name to be checked.
   * @return The owning service group and never <code>null</code>.
   * @throws SMPUnauthorizedException
   *         If the participant identifier is not owned by the user specified in
   *         the credentials
   */
  @Nonnull
  private static ISMPServiceGroup _verifyOwnership (@Nonnull final ParticipantIdentifierType aServiceGroupID,
                                                    @Nonnull final String sUserLoginName) throws SMPUnauthorizedException
  {
    // Resolve user group
    final ISMPServiceGroup aServiceGroup = MetaManager.getServiceGroupMgr ().getSMPServiceGroupOfID (aServiceGroupID);
    if (aServiceGroup == null)
    {
      throw new SMPUnauthorizedException ("Service group " +
                                          IdentifierHelper.getIdentifierURIEncoded (aServiceGroupID) +
                                          " does not exist");
    }

    // Resolve user
    final String sOwnerID = aServiceGroup.getOwnerID ();
    final IUser aOwner = AccessManager.getInstance ().getUserOfID (sOwnerID);
    if (aOwner == null || !aOwner.getLoginName ().equals (sUserLoginName))
    {
      throw new SMPUnauthorizedException ("User '" +
                                          sUserLoginName +
                                          "' does not own " +
                                          IdentifierHelper.getIdentifierURIEncoded (aServiceGroupID));
    }

    if (s_aLogger.isDebugEnabled ())
      s_aLogger.debug ("Verified service group ID " +
                       aServiceGroup.getID () +
                       " is owned by user '" +
                       sUserLoginName +
                       "'");

    return aServiceGroup;
  }

  @Nonnull
  @ReturnsMutableCopy
  public Collection <ParticipantIdentifierType> getServiceGroupList (@Nonnull final IDataUser aDataUser)
  {
    final DAODataUser aUser = (DAODataUser) aDataUser;

    final ISMPServiceGroupManager aServiceGroupMgr = MetaManager.getServiceGroupMgr ();
    final List <ParticipantIdentifierType> ret = new ArrayList <ParticipantIdentifierType> ();
    for (final ISMPServiceGroup aServiceGroup : aServiceGroupMgr.getAllSMPServiceGroups ())
      if (aServiceGroup.getOwnerID ().equals (aUser.getID ()))
        ret.add (new SimpleParticipantIdentifier (aServiceGroup.getParticpantIdentifier ()));
    return ret;
  }

  @Nullable
  public ServiceGroupType getServiceGroup (@Nonnull final ParticipantIdentifierType aServiceGroupID)
  {
    final ISMPServiceGroupManager aServiceGroupMgr = MetaManager.getServiceGroupMgr ();
    final ISMPServiceGroup aSG = aServiceGroupMgr.getSMPServiceGroupOfID (aServiceGroupID);
    return aSG == null ? null : aSG.getAsJAXBObject ();
  }

  public void saveServiceGroupInternal (@Nonnull final ParticipantIdentifierType aParticipantID,
                                        @Nullable final String sExtension,
                                        @Nonnull final String sOwningUserID) throws SMPUnauthorizedException,
                                                                             RegistrationHookException
  {
    final ISMPServiceGroupManager aServiceGroupMgr = MetaManager.getServiceGroupMgr ();
    final ISMPServiceGroup aSG = aServiceGroupMgr.getSMPServiceGroupOfID (aParticipantID);
    if (aSG != null)
    {
      // Check that existing ServiceGroup can be updated
      _verifyOwnership (aParticipantID, AccessManager.getInstance ().getUserOfID (sOwningUserID).getLoginName ());

      // Update existing service group (don't change the owner)
      aServiceGroupMgr.updateSMPServiceGroup (aSG.getID (), aSG.getOwnerID (), sExtension);
    }
    else
    {
      // It's a new service group
      m_aHook.createServiceGroup (aParticipantID);

      try
      {
        // Create new
        aServiceGroupMgr.createSMPServiceGroup (sOwningUserID, aParticipantID, sExtension);
      }
      catch (final RuntimeException ex)
      {
        // An error occurred - remove from SML again
        m_aHook.undoCreateServiceGroup (aParticipantID);
        throw ex;
      }
    }
  }

  public void saveServiceGroup (@Nonnull final ServiceGroupType aServiceGroup,
                                @Nonnull final IDataUser aDataUser) throws SMPUnauthorizedException,
                                                                    RegistrationHookException
  {
    final DAODataUser aUser = (DAODataUser) aDataUser;
    saveServiceGroupInternal (aServiceGroup.getParticipantIdentifier (),
                              SMPExtensionConverter.convertToString (aServiceGroup.getExtension ()),
                              aUser.getID ());
  }

  public void deleteServiceGroup (@Nonnull final ParticipantIdentifierType aServiceGroupID,
                                  @Nonnull final IDataUser aDataUser) throws SMPUnauthorizedException,
                                                                      RegistrationHookException
  {
    final ISMPServiceGroupManager aServiceGroupMgr = MetaManager.getServiceGroupMgr ();
    final ISMPServiceGroup aServiceGroup = aServiceGroupMgr.getSMPServiceGroupOfID (aServiceGroupID);
    if (aServiceGroup == null)
      throw new SMPNotFoundException (SMPHelper.createSMPServiceGroupID (aServiceGroupID));

    // Check that the passed user is really the owner
    _verifyOwnership (aServiceGroupID, aDataUser.getUserName ());

    // delete in SML first - throws an exception in case of error
    m_aHook.deleteServiceGroup (aServiceGroupID);

    try
    {
      // delete from SMP service group manager
      if (aServiceGroupMgr.deleteSMPServiceGroup (aServiceGroup).isUnchanged ())
        throw new SMPNotFoundException (SMPHelper.createSMPServiceGroupID (aServiceGroupID));
    }
    catch (final RuntimeException ex)
    {
      // An error occurred - remove from SML again
      m_aHook.undoDeleteServiceGroup (aServiceGroupID);
      throw ex;
    }

    // Delete all redirects and all service information of this service group
    // as well
    MetaManager.getRedirectMgr ().deleteAllSMPRedirectsOfServiceGroup (aServiceGroup);
    MetaManager.getServiceInformationMgr ().deleteAllSMPServiceInformationOfServiceGroup (aServiceGroup);
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <DocumentIdentifierType> getDocumentTypes (@Nonnull final ParticipantIdentifierType aServiceGroupID)
  {
    final String sServiceGroupID = SMPHelper.createSMPServiceGroupID (aServiceGroupID);

    final ISMPServiceInformationManager aServiceInformationMgr = MetaManager.getServiceInformationMgr ();
    final List <DocumentIdentifierType> ret = new ArrayList <DocumentIdentifierType> ();
    for (final ISMPServiceInformation aServiceInformation : aServiceInformationMgr.getAllSMPServiceInformationsOfServiceGroup (sServiceGroupID))
      ret.add (new SimpleDocumentTypeIdentifier (aServiceInformation.getDocumentTypeIdentifier ()));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public Collection <ServiceMetadataType> getServices (@Nonnull final ParticipantIdentifierType aServiceGroupID)
  {
    final String sServiceGroupID = SMPHelper.createSMPServiceGroupID (aServiceGroupID);

    final ISMPServiceInformationManager aServiceInformationMgr = MetaManager.getServiceInformationMgr ();
    final List <ServiceMetadataType> ret = new ArrayList <ServiceMetadataType> ();
    for (final ISMPServiceInformation aServiceInformation : aServiceInformationMgr.getAllSMPServiceInformationsOfServiceGroup (sServiceGroupID))
      ret.add (aServiceInformation.getAsJAXBObject ());
    return ret;
  }

  @Nullable
  private ISMPServiceInformation _getMatchingServiceMetadata (@Nonnull final ParticipantIdentifierType aServiceGroupID,
                                                              @Nonnull final DocumentIdentifierType aDocTypeID)
  {
    final String sServiceGroupID = SMPHelper.createSMPServiceGroupID (aServiceGroupID);

    final ISMPServiceInformationManager aServiceInformationMgr = MetaManager.getServiceInformationMgr ();
    return aServiceInformationMgr.getSMPServiceInformationOfServiceGroupAndDocumentType (sServiceGroupID, aDocTypeID);
  }

  @Nullable
  public ServiceMetadataType getService (@Nonnull final ParticipantIdentifierType aServiceGroupID,
                                         @Nonnull final DocumentIdentifierType aDocTypeID)
  {
    final ISMPServiceInformation aMatch = _getMatchingServiceMetadata (aServiceGroupID, aDocTypeID);
    return aMatch == null ? null : aMatch.getAsJAXBObject ();
  }

  public void saveService (@Nonnull final ServiceInformationType aServiceInformation,
                           @Nonnull final IDataUser aDataUser)
  {
    final ISMPServiceGroup aServiceGroup = _verifyOwnership (aServiceInformation.getParticipantIdentifier (),
                                                             aDataUser.getUserName ());

    final ISMPServiceInformationManager aServiceInformationMgr = MetaManager.getServiceInformationMgr ();
    aServiceInformationMgr.createSMPServiceInformation (SMPServiceInformation.createFromJAXB (aServiceGroup,
                                                                                              aServiceInformation));
  }

  public void deleteService (@Nonnull final ParticipantIdentifierType aServiceGroupID,
                             @Nonnull final DocumentIdentifierType aDocTypeID,
                             @Nonnull final IDataUser aDataUser)
  {
    _verifyOwnership (aServiceGroupID, aDataUser.getUserName ());

    final ISMPServiceInformation aMatch = _getMatchingServiceMetadata (aServiceGroupID, aDocTypeID);
    final ISMPServiceInformationManager aServiceInformationMgr = MetaManager.getServiceInformationMgr ();
    if (aMatch == null || aServiceInformationMgr.deleteSMPServiceInformation (aMatch).isUnchanged ())
      throw new SMPNotFoundException (SMPHelper.createSMPServiceGroupID (aServiceGroupID));
  }

  @Nullable
  public ServiceMetadataType getRedirection (@Nonnull final ParticipantIdentifierType aServiceGroupID,
                                             @Nonnull final DocumentIdentifierType aDocTypeID)
  {
    final ISMPRedirectManager aRedirectMgr = MetaManager.getRedirectMgr ();
    final ISMPRedirect aRedirect = aRedirectMgr.getSMPRedirectOfServiceGroupAndDocumentType (SMPHelper.createSMPServiceGroupID (aServiceGroupID),
                                                                                             aDocTypeID);
    return aRedirect == null ? null : aRedirect.getAsJAXBObject ();
  }
}
