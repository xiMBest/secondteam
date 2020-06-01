import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInsuarence, Insuarence } from 'app/shared/model/insuarence.model';
import { InsuarenceService } from './insuarence.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-insuarence-update',
  templateUrl: './insuarence-update.component.html'
})
export class InsuarenceUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    dateApply: [],
    dateEnd: [],
    cost: [],
    type: [],
    customer: []
  });

  constructor(
    protected insuarenceService: InsuarenceService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ insuarence }) => {
      if (!insuarence.id) {
        const today = moment().startOf('day');
        insuarence.dateApply = today;
        insuarence.dateEnd = today;
      }

      this.updateForm(insuarence);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(insuarence: IInsuarence): void {
    this.editForm.patchValue({
      id: insuarence.id,
      dateApply: insuarence.dateApply ? insuarence.dateApply.format(DATE_TIME_FORMAT) : null,
      dateEnd: insuarence.dateEnd ? insuarence.dateEnd.format(DATE_TIME_FORMAT) : null,
      cost: insuarence.cost,
      type: insuarence.type,
      customer: insuarence.customer
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const insuarence = this.createFromForm();
    if (insuarence.id !== undefined) {
      this.subscribeToSaveResponse(this.insuarenceService.update(insuarence));
    } else {
      this.subscribeToSaveResponse(this.insuarenceService.create(insuarence));
    }
  }

  private createFromForm(): IInsuarence {
    return {
      ...new Insuarence(),
      id: this.editForm.get(['id'])!.value,
      dateApply: this.editForm.get(['dateApply'])!.value ? moment(this.editForm.get(['dateApply'])!.value, DATE_TIME_FORMAT) : undefined,
      dateEnd: this.editForm.get(['dateEnd'])!.value ? moment(this.editForm.get(['dateEnd'])!.value, DATE_TIME_FORMAT) : undefined,
      cost: this.editForm.get(['cost'])!.value,
      type: this.editForm.get(['type'])!.value,
      customer: this.editForm.get(['customer'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInsuarence>>): void {
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
