import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAutopark } from 'app/shared/model/autopark.model';

@Component({
  selector: 'jhi-autopark-detail',
  templateUrl: './autopark-detail.component.html'
})
export class AutoparkDetailComponent implements OnInit {
  autopark: IAutopark | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autopark }) => (this.autopark = autopark));
  }

  previousState(): void {
    window.history.back();
  }
}
