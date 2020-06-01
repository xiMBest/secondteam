import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInsuarence } from 'app/shared/model/insuarence.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { InsuarenceService } from './insuarence.service';
import { InsuarenceDeleteDialogComponent } from './insuarence-delete-dialog.component';

@Component({
  selector: 'jhi-insuarence',
  templateUrl: './insuarence.component.html'
})
export class InsuarenceComponent implements OnInit, OnDestroy {
  insuarences: IInsuarence[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected insuarenceService: InsuarenceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.insuarences = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.insuarenceService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IInsuarence[]>) => this.paginateInsuarences(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.insuarences = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInsuarences();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInsuarence): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInsuarences(): void {
    this.eventSubscriber = this.eventManager.subscribe('insuarenceListModification', () => this.reset());
  }

  delete(insuarence: IInsuarence): void {
    const modalRef = this.modalService.open(InsuarenceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.insuarence = insuarence;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateInsuarences(data: IInsuarence[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.insuarences.push(data[i]);
      }
    }
  }
}
