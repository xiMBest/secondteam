import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRate } from 'app/shared/model/rate.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RateService } from './rate.service';
import { RateDeleteDialogComponent } from './rate-delete-dialog.component';

@Component({
  selector: 'jhi-rate',
  templateUrl: './rate.component.html'
})
export class RateComponent implements OnInit, OnDestroy {
  rates: IRate[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected rateService: RateService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.rates = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.rateService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IRate[]>) => this.paginateRates(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.rates = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRate): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRates(): void {
    this.eventSubscriber = this.eventManager.subscribe('rateListModification', () => this.reset());
  }

  delete(rate: IRate): void {
    const modalRef = this.modalService.open(RateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rate = rate;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRates(data: IRate[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.rates.push(data[i]);
      }
    }
  }
}
