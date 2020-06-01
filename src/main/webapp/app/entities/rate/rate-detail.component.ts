import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRate } from 'app/shared/model/rate.model';

@Component({
  selector: 'jhi-rate-detail',
  templateUrl: './rate-detail.component.html'
})
export class RateDetailComponent implements OnInit {
  rate: IRate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rate }) => (this.rate = rate));
  }

  previousState(): void {
    window.history.back();
  }
}
