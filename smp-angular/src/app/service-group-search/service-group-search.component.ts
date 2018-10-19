///<reference path="../smp.constants.ts"/>
import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {ColumnPicker} from '../common/column-picker/column-picker.model';
import {MatDialog, MatDialogRef} from '@angular/material';
import {AlertService} from '../alert/alert.service';
import {ServiceGroupSearchController} from './service-group-search-controller';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs/index";
import {SearchTableResult} from "../common/search-table/search-table-result.model";
import {DomainRo} from "../domain/domain-ro.model";
import {SmpConstants} from "../smp.constants";
import {GlobalLookups} from "../common/global-lookups";

@Component({
  moduleId: module.id,
  templateUrl: './service-group-search.component.html',
  styleUrls: ['./service-group-search.component.css']
})
export class ServiceGroupSearchComponent implements OnInit {

  @ViewChild('rowExtensionAction') rowExtensionAction: TemplateRef<any>
  @ViewChild('rowSMPUrlLinkAction') rowSMPUrlLinkAction: TemplateRef<any>
  @ViewChild('rowActions') rowActions: TemplateRef<any>;

  columnPicker: ColumnPicker = new ColumnPicker();
  serviceGroupSearchController: ServiceGroupSearchController;
  filter: any = {};
  domainlist: Array<any>;
  contextPath: string = location.pathname.substring(0, location.pathname.length - 3); // remove /ui s
  baseUrl: string = SmpConstants.REST_SEARCH;

  constructor(protected lookups: GlobalLookups, protected http: HttpClient, protected alertService: AlertService, public dialog: MatDialog) {

  }

  ngOnDestroy() {

  }

  ngOnInit() {
    this.lookups.getDomainLookupObservable().subscribe((domains: SearchTableResult) => {
      this.domainlist = new Array(domains.serviceEntities.length)
        .map((v, index) => domains.serviceEntities[index] as DomainRo);

      this.domainlist = domains.serviceEntities.map(serviceEntity => {
        return {...serviceEntity}
      });
    });

    this.serviceGroupSearchController = new ServiceGroupSearchController(this.dialog);

    this.columnPicker.allColumns = [
      {
        name: 'Metadata size',
        prop: 'serviceMetadata.length',
        width: 80,
        maxWidth: 120
      },
      {
        name: 'Participant scheme',
        prop: 'participantScheme',
        maxWidth: 300
      },
      {
        name: 'Participant identifier',
        prop: 'participantIdentifier',
      },
      {
        cellTemplate: this.rowSMPUrlLinkAction,
        name: 'OASIS ServiceGroup URL',
        width: 150,
        maxWidth: 250,
        sortable: false
      },
    ];


    this.columnPicker.selectedColumns = this.columnPicker.allColumns.filter(col => {
      return ["Metadata size", "Participant scheme", "Participant identifier", "OASIS ServiceGroup URL"].indexOf(col.name) != -1
    });
  }

  details(row: any) {
    this.serviceGroupSearchController.showDetails(row);

  }
}
