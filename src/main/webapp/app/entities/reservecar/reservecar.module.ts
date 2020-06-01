import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RentautoSharedModule } from 'app/shared/shared.module';
import { ReservecarComponent } from './reservecar.component';
import { ReservecarDetailComponent } from './reservecar-detail.component';
import { ReservecarUpdateComponent } from './reservecar-update.component';
import { ReservecarDeleteDialogComponent } from './reservecar-delete-dialog.component';
import { reservecarRoute } from './reservecar.route';

@NgModule({
  imports: [RentautoSharedModule, RouterModule.forChild(reservecarRoute)],
  declarations: [ReservecarComponent, ReservecarDetailComponent, ReservecarUpdateComponent, ReservecarDeleteDialogComponent],
  entryComponents: [ReservecarDeleteDialogComponent]
})
export class RentautoReservecarModule {}
