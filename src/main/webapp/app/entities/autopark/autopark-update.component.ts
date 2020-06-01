import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAutopark, Autopark } from 'app/shared/model/autopark.model';
import { AutoparkService } from './autopark.service';

@Component({
  selector: 'jhi-autopark-update',
  templateUrl: './autopark-update.component.html'
})
export class AutoparkUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    mark: [null, [Validators.required, Validators.maxLength(100)]],
    model: [null, [Validators.required, Validators.maxLength(100)]],
    prePrice: [],
    color: [null, [Validators.required, Validators.maxLength(20)]],
    type: [],
    pledge: [],
    statusAvaileble: []
  });

  constructor(protected autoparkService: AutoparkService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autopark }) => {
      this.updateForm(autopark);
    });
  }

  updateForm(autopark: IAutopark): void {
    this.editForm.patchValue({
      id: autopark.id,
      mark: autopark.mark,
      model: autopark.model,
      prePrice: autopark.prePrice,
      color: autopark.color,
      type: autopark.type,
      pledge: autopark.pledge,
      statusAvaileble: autopark.statusAvaileble
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autopark = this.createFromForm();
    if (autopark.id !== undefined) {
      this.subscribeToSaveResponse(this.autoparkService.update(autopark));
    } else {
      this.subscribeToSaveResponse(this.autoparkService.create(autopark));
    }
  }

  private createFromForm(): IAutopark {
    return {
      ...new Autopark(),
      id: this.editForm.get(['id'])!.value,
      mark: this.editForm.get(['mark'])!.value,
      model: this.editForm.get(['model'])!.value,
      prePrice: this.editForm.get(['prePrice'])!.value,
      color: this.editForm.get(['color'])!.value,
      type: this.editForm.get(['type'])!.value,
      pledge: this.editForm.get(['pledge'])!.value,
      statusAvaileble: this.editForm.get(['statusAvaileble'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutopark>>): void {
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
}
