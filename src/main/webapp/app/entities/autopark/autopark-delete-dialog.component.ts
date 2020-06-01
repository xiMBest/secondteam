import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAutopark } from 'app/shared/model/autopark.model';
import { AutoparkService } from './autopark.service';

@Component({
  templateUrl: './autopark-delete-dialog.component.html'
})
export class AutoparkDeleteDialogComponent {
  autopark?: IAutopark;

  constructor(protected autoparkService: AutoparkService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.autoparkService.delete(id).subscribe(() => {
      this.eventManager.broadcast('autoparkListModification');
      this.activeModal.close();
    });
  }
}
