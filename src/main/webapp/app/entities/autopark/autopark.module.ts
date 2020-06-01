import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RentautoSharedModule } from 'app/shared/shared.module';
import { AutoparkComponent } from './autopark.component';
import { AutoparkDetailComponent } from './autopark-detail.component';
import { AutoparkUpdateComponent } from './autopark-update.component';
import { AutoparkDeleteDialogComponent } from './autopark-delete-dialog.component';
import { autoparkRoute } from './autopark.route';

@NgModule({
  imports: [RentautoSharedModule, RouterModule.forChild(autoparkRoute)],
  declarations: [AutoparkComponent, AutoparkDetailComponent, AutoparkUpdateComponent, AutoparkDeleteDialogComponent],
  entryComponents: [AutoparkDeleteDialogComponent]
})
export class RentautoAutoparkModule {}
