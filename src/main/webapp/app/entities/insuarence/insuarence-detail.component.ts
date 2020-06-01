import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInsuarence } from 'app/shared/model/insuarence.model';

@Component({
  selector: 'jhi-insuarence-detail',
  templateUrl: './insuarence-detail.component.html'
})
export class InsuarenceDetailComponent implements OnInit {
  insuarence: IInsuarence | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ insuarence }) => (this.insuarence = insuarence));
  }

  previousState(): void {
    window.history.back();
  }
}
