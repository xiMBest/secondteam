import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRate, Rate } from 'app/shared/model/rate.model';
import { RateService } from './rate.service';
import { IAutopark } from 'app/shared/model/autopark.model';
import { AutoparkService } from 'app/entities/autopark/autopark.service';

@Component({
  selector: 'jhi-rate-update',
  templateUrl: './rate-update.component.html'
})
export class RateUpdateComponent implements OnInit {
  isSaving = false;
  autoparks: IAutopark[] = [];

  editForm = this.fb.group({
    id: [],
    raiting: [],
    autopark: []
  });

  constructor(
    protected rateService: RateService,
    protected autoparkService: AutoparkService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rate }) => {
      this.updateForm(rate);

      this.autoparkService.query().subscribe((res: HttpResponse<IAutopark[]>) => (this.autoparks = res.body || []));
    });
  }

  updateForm(rate: IRate): void {
    this.editForm.patchValue({
      id: rate.id,
      raiting: rate.raiting,
      autopark: rate.autopark
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rate = this.createFromForm();
    if (rate.id !== undefined) {
      this.subscribeToSaveResponse(this.rateService.update(rate));
    } else {
      this.subscribeToSaveResponse(this.rateService.create(rate));
    }
  }

  private createFromForm(): IRate {
    return {
      ...new Rate(),
      id: this.editForm.get(['id'])!.value,
      raiting: this.editForm.get(['raiting'])!.value,
      autopark: this.editForm.get(['autopark'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRate>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IAutopark): any {
    return item.id;
  }
}
