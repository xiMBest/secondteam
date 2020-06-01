import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFixcar } from 'app/shared/model/fixcar.model';
import { FixcarService } from './fixcar.service';

@Component({
  templateUrl: './fixcar-delete-dialog.component.html'
})
export class FixcarDeleteDialogComponent {
  fixcar?: IFixcar;

  constructor(protected fixcarService: FixcarService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fixcarService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fixcarListModification');
      this.activeModal.close();
    });
  }
}
