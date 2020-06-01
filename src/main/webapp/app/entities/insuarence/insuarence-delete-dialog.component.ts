import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInsuarence } from 'app/shared/model/insuarence.model';
import { InsuarenceService } from './insuarence.service';

@Component({
  templateUrl: './insuarence-delete-dialog.component.html'
})
export class InsuarenceDeleteDialogComponent {
  insuarence?: IInsuarence;

  constructor(
    protected insuarenceService: InsuarenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.insuarenceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('insuarenceListModification');
      this.activeModal.close();
    });
  }
}
