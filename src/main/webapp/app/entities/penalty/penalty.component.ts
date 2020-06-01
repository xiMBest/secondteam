import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPenalty } from 'app/shared/model/penalty.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PenaltyService } from './penalty.service';
import { PenaltyDeleteDialogComponent } from './penalty-delete-dialog.component';

@Component({
  selector: 'jhi-penalty',
  templateUrl: './penalty.component.html'
})
export class PenaltyComponent implements OnInit, OnDestroy {
  penalties: IPenalty[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected penaltyService: PenaltyService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.penalties = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.penaltyService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPenalty[]>) => this.paginatePenalties(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.penalties = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPenalties();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPenalty): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPenalties(): void {
    this.eventSubscriber = this.eventManager.subscribe('penaltyListModification', () => this.reset());
  }

  delete(penalty: IPenalty): void {
    const modalRef = this.modalService.open(PenaltyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.penalty = penalty;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePenalties(data: IPenalty[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.penalties.push(data[i]);
      }
    }
  }
}
