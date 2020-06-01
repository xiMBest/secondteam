import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RentautoSharedModule } from 'app/shared/shared.module';
import { PenaltyComponent } from './penalty.component';
import { PenaltyDetailComponent } from './penalty-detail.component';
import { PenaltyUpdateComponent } from './penalty-update.component';
import { PenaltyDeleteDialogComponent } from './penalty-delete-dialog.component';
import { penaltyRoute } from './penalty.route';

@NgModule({
  imports: [RentautoSharedModule, RouterModule.forChild(penaltyRoute)],
  declarations: [PenaltyComponent, PenaltyDetailComponent, PenaltyUpdateComponent, PenaltyDeleteDialogComponent],
  entryComponents: [PenaltyDeleteDialogComponent]
})
export class RentautoPenaltyModule {}
