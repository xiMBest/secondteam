import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReservecar } from 'app/shared/model/reservecar.model';

@Component({
  selector: 'jhi-reservecar-detail',
  templateUrl: './reservecar-detail.component.html'
})
export class ReservecarDetailComponent implements OnInit {
  reservecar: IReservecar | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reservecar }) => (this.reservecar = reservecar));
  }

  previousState(): void {
    window.history.back();
  }
}
