import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFixcar } from 'app/shared/model/fixcar.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FixcarService } from './fixcar.service';
import { FixcarDeleteDialogComponent } from './fixcar-delete-dialog.component';

@Component({
  selector: 'jhi-fixcar',
  templateUrl: './fixcar.component.html'
})
export class FixcarComponent implements OnInit, OnDestroy {
  fixcars: IFixcar[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected fixcarService: FixcarService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.fixcars = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.fixcarService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IFixcar[]>) => this.paginateFixcars(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.fixcars = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFixcars();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFixcar): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFixcars(): void {
    this.eventSubscriber = this.eventManager.subscribe('fixcarListModification', () => this.reset());
  }

  delete(fixcar: IFixcar): void {
    const modalRef = this.modalService.open(FixcarDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.fixcar = fixcar;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFixcars(data: IFixcar[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.fixcars.push(data[i]);
      }
    }
  }
}
