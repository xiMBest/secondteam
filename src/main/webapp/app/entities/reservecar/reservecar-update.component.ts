import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IReservecar, Reservecar } from 'app/shared/model/reservecar.model';
import { ReservecarService } from './reservecar.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-reservecar-update',
  templateUrl: './reservecar-update.component.html'
})
export class ReservecarUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    type: [],
    totalPrice: [],
    datePickUP: [],
    dateDropDown: [],
    description: [],
    customer: []
  });

  constructor(
    protected reservecarService: ReservecarService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reservecar }) => {
      if (!reservecar.id) {
        const today = moment().startOf('day');
        reservecar.datePickUP = today;
        reservecar.dateDropDown = today;
      }

      this.updateForm(reservecar);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(reservecar: IReservecar): void {
    this.editForm.patchValue({
      id: reservecar.id,
      name: reservecar.name,
      type: reservecar.type,
      totalPrice: reservecar.totalPrice,
      datePickUP: reservecar.datePickUP ? reservecar.datePickUP.format(DATE_TIME_FORMAT) : null,
      dateDropDown: reservecar.dateDropDown ? reservecar.dateDropDown.format(DATE_TIME_FORMAT) : null,
      description: reservecar.description,
      customer: reservecar.customer
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reservecar = this.createFromForm();
    if (reservecar.id !== undefined) {
      this.subscribeToSaveResponse(this.reservecarService.update(reservecar));
    } else {
      this.subscribeToSaveResponse(this.reservecarService.create(reservecar));
    }
  }

  private createFromForm(): IReservecar {
    return {
      ...new Reservecar(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      type: this.editForm.get(['type'])!.value,
      totalPrice: this.editForm.get(['totalPrice'])!.value,
      datePickUP: this.editForm.get(['datePickUP'])!.value ? moment(this.editForm.get(['datePickUP'])!.value, DATE_TIME_FORMAT) : undefined,
      dateDropDown: this.editForm.get(['dateDropDown'])!.value
        ? moment(this.editForm.get(['dateDropDown'])!.value, DATE_TIME_FORMAT)
        : undefined,
      description: this.editForm.get(['description'])!.value,
      customer: this.editForm.get(['customer'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReservecar>>): void {
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
