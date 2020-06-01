import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPenalty, Penalty } from 'app/shared/model/penalty.model';
import { PenaltyService } from './penalty.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-penalty-update',
  templateUrl: './penalty-update.component.html'
})
export class PenaltyUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    datePenalty: [],
    price: [],
    description: [],
    customer: []
  });

  constructor(
    protected penaltyService: PenaltyService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ penalty }) => {
      if (!penalty.id) {
        const today = moment().startOf('day');
        penalty.datePenalty = today;
      }

      this.updateForm(penalty);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(penalty: IPenalty): void {
    this.editForm.patchValue({
      id: penalty.id,
      datePenalty: penalty.datePenalty ? penalty.datePenalty.format(DATE_TIME_FORMAT) : null,
      price: penalty.price,
      description: penalty.description,
      customer: penalty.customer
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const penalty = this.createFromForm();
    if (penalty.id !== undefined) {
      this.subscribeToSaveResponse(this.penaltyService.update(penalty));
    } else {
      this.subscribeToSaveResponse(this.penaltyService.create(penalty));
    }
  }

  private createFromForm(): IPenalty {
    return {
      ...new Penalty(),
      id: this.editForm.get(['id'])!.value,
      datePenalty: this.editForm.get(['datePenalty'])!.value
        ? moment(this.editForm.get(['datePenalty'])!.value, DATE_TIME_FORMAT)
        : undefined,
      price: this.editForm.get(['price'])!.value,
      description: this.editForm.get(['description'])!.value,
      customer: this.editForm.get(['customer'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPenalty>>): void {
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

  trackById(index: number, item: ICustomer): any {
    return item.id;
  }
}
