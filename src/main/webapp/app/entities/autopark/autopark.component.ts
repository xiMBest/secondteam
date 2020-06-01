import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAutopark } from 'app/shared/model/autopark.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AutoparkService } from './autopark.service';
import { AutoparkDeleteDialogComponent } from './autopark-delete-dialog.component';

@Component({
  selector: 'jhi-autopark',
  templateUrl: './autopark.component.html'
})
export class AutoparkComponent implements OnInit, OnDestroy {
  autoparks: IAutopark[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected autoparkService: AutoparkService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.autoparks = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.autoparkService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAutopark[]>) => this.paginateAutoparks(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.autoparks = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAutoparks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAutopark): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAutoparks(): void {
    this.eventSubscriber = this.eventManager.subscribe('autoparkListModification', () => this.reset());
  }

  delete(autopark: IAutopark): void {
    const modalRef = this.modalService.open(AutoparkDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.autopark = autopark;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAutoparks(data: IAutopark[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.autoparks.push(data[i]);
      }
    }
  }
}
