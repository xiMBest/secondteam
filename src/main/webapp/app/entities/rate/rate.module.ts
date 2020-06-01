import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RentautoSharedModule } from 'app/shared/shared.module';
import { RateComponent } from './rate.component';
import { RateDetailComponent } from './rate-detail.component';
import { RateUpdateComponent } from './rate-update.component';
import { RateDeleteDialogComponent } from './rate-delete-dialog.component';
import { rateRoute } from './rate.route';

@NgModule({
  imports: [RentautoSharedModule, RouterModule.forChild(rateRoute)],
  declarations: [RateComponent, RateDetailComponent, RateUpdateComponent, RateDeleteDialogComponent],
  entryComponents: [RateDeleteDialogComponent]
})
export class RentautoRateModule {}
