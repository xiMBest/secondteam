import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFixcar } from 'app/shared/model/fixcar.model';

@Component({
  selector: 'jhi-fixcar-detail',
  templateUrl: './fixcar-detail.component.html'
})
export class FixcarDetailComponent implements OnInit {
  fixcar: IFixcar | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fixcar }) => (this.fixcar = fixcar));
  }

  previousState(): void {
    window.history.back();
  }
}
