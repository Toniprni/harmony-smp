import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {SearchTableResult} from "../../../common/search-table/search-table-result.model";
import {User} from "../../../security/user.model";
import {HttpClient, HttpParams} from "@angular/common/http";
import {SmpConstants} from "../../../smp.constants";
import {SecurityService} from "../../../security/security.service";
import {AlertMessageService} from "../../../common/alert-message/alert-message.service";
import {MemberRo} from "./member-ro.model";
import {TableResult} from "./table-result.model";
import {SearchUserRo} from "./member-dialog/search-user-ro.model";
import {MembershipRoleEnum} from "./membership-role.enum";


@Injectable()
export class MembershipService {


  constructor(
    private http: HttpClient,
    private securityService: SecurityService,
    private alertService: AlertMessageService) {
  }


  getDomainMembersObservable(domainID: string, filter: any, page: number, pageSize: number): Observable<SearchTableResult> {
    const currentUser: User = this.securityService.getCurrentUser();

    let params: HttpParams = new HttpParams()
      .set('page', page.toString())
      .set('pageSize', pageSize.toString());

    for (let filterProperty in filter) {
      if (filter.hasOwnProperty(filterProperty)) {
        // must encode else problem with + sign
        params = params.set(filterProperty, encodeURIComponent(filter[filterProperty]));
      }
    }

    return this.http.get<TableResult<MemberRo>>(SmpConstants.REST_PUBLIC_DOMAIN_MEMBERS
      .replace(SmpConstants.PATH_PARAM_ENC_USER_ID, currentUser.userId)
      .replace(SmpConstants.PATH_PARAM_ENC_DOMAIN_ID, domainID), {params});
  }

  getUserLookupObservable(filter: string): Observable<SearchUserRo[]> {
    const currentUser: User = this.securityService.getCurrentUser();
    let params: HttpParams = new HttpParams()
      .set('filter', filter);
    return this.http.get<SearchUserRo[]>(SmpConstants.REST_PUBLIC_USER_SEARCH
      .replace(SmpConstants.PATH_PARAM_ENC_USER_ID, currentUser.userId), {params});
  }


  addEditMemberToDomain(domainId: string, member: MemberRo): Observable<MemberRo> {
    const currentUser: User = this.securityService.getCurrentUser();

    return this.http.put<MemberRo>(SmpConstants.REST_PUBLIC_DOMAIN_MEMBERS_ADD
      .replace(SmpConstants.PATH_PARAM_ENC_USER_ID, currentUser.userId)
      .replace(SmpConstants.PATH_PARAM_ENC_DOMAIN_ID, domainId),member);
  }

  deleteMemberFromDomain( domainId: string, member: MemberRo): Observable<MemberRo> {
    const currentUser: User = this.securityService.getCurrentUser();

    return this.http.delete<MemberRo>(SmpConstants.REST_PUBLIC_DOMAIN_MEMBERS_DELETE
      .replace(SmpConstants.PATH_PARAM_ENC_USER_ID, currentUser.userId)
      .replace(SmpConstants.PATH_PARAM_ENC_DOMAIN_ID, domainId)
      .replace(SmpConstants.PATH_PARAM_ENC_MEMBER_ID, member.memberId));
  }

}
