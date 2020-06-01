import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReservecar } from 'app/shared/model/reservecar.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ReservecarService } from './reservecar.service';
import { ReservecarDeleteDialogComponent } from './reservecar-delete-dialog.component';

@Component({
  selector: 'jhi-reservecar',
  templateUrl: './reservecar.component.html'
})
export class ReservecarComponent implements OnInit, OnDestroy {
  reservecars: IReservecar[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected reservecarService: ReservecarService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.reservecars = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.reservecarService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IReservecar[]>) => this.paginateReservecars(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.reservecars = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReservecars();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReservecar): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReservecars(): void {
    this.eventSubscriber = this.eventManager.subscribe('reservecarListModification', () => this.reset());
  }

  delete(reservecar: IReservecar): void {
    const modalRef = this.modalService.open(ReservecarDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reservecar = reservecar;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateReservecars(data: IReservecar[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.reservecars.push(data[i]);
      }
    }
  }
}
