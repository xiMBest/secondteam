import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReservecar } from 'app/shared/model/reservecar.model';
import { ReservecarService } from './reservecar.service';

@Component({
  templateUrl: './reservecar-delete-dialog.component.html'
})
export class ReservecarDeleteDialogComponent {
  reservecar?: IReservecar;

  constructor(
    protected reservecarService: ReservecarService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reservecarService.delete(id).subscribe(() => {
      this.eventManager.broadcast('reservecarListModification');
      this.activeModal.close();
    });
  }
}
