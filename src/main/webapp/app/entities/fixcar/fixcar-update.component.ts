import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFixcar, Fixcar } from 'app/shared/model/fixcar.model';
import { FixcarService } from './fixcar.service';
import { IAutopark } from 'app/shared/model/autopark.model';
import { AutoparkService } from 'app/entities/autopark/autopark.service';

@Component({
  selector: 'jhi-fixcar-update',
  templateUrl: './fixcar-update.component.html'
})
export class FixcarUpdateComponent implements OnInit {
  isSaving = false;
  autoparks: IAutopark[] = [];

  editForm = this.fb.group({
    id: [],
    price: [],
    dateFixing: [],
    description: [],
    autoparks: []
  });

  constructor(
    protected fixcarService: FixcarService,
    protected autoparkService: AutoparkService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fixcar }) => {
      if (!fixcar.id) {
        const today = moment().startOf('day');
        fixcar.dateFixing = today;
      }

      this.updateForm(fixcar);

      this.autoparkService.query().subscribe((res: HttpResponse<IAutopark[]>) => (this.autoparks = res.body || []));
    });
  }

  updateForm(fixcar: IFixcar): void {
    this.editForm.patchValue({
      id: fixcar.id,
      price: fixcar.price,
      dateFixing: fixcar.dateFixing ? fixcar.dateFixing.format(DATE_TIME_FORMAT) : null,
      description: fixcar.description,
      autoparks: fixcar.autoparks
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fixcar = this.createFromForm();
    if (fixcar.id !== undefined) {
      this.subscribeToSaveResponse(this.fixcarService.update(fixcar));
    } else {
      this.subscribeToSaveResponse(this.fixcarService.create(fixcar));
    }
  }

  private createFromForm(): IFixcar {
    return {
      ...new Fixcar(),
      id: this.editForm.get(['id'])!.value,
      price: this.editForm.get(['price'])!.value,
      dateFixing: this.editForm.get(['dateFixing'])!.value ? moment(this.editForm.get(['dateFixing'])!.value, DATE_TIME_FORMAT) : undefined,
      description: this.editForm.get(['description'])!.value,
      autoparks: this.editForm.get(['autoparks'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFixcar>>): void {
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

  getSelected(selectedVals: IAutopark[], option: IAutopark): IAutopark {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
