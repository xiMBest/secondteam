import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RentautoSharedModule } from 'app/shared/shared.module';
import { InsuarenceComponent } from './insuarence.component';
import { InsuarenceDetailComponent } from './insuarence-detail.component';
import { InsuarenceUpdateComponent } from './insuarence-update.component';
import { InsuarenceDeleteDialogComponent } from './insuarence-delete-dialog.component';
import { insuarenceRoute } from './insuarence.route';

@NgModule({
  imports: [RentautoSharedModule, RouterModule.forChild(insuarenceRoute)],
  declarations: [InsuarenceComponent, InsuarenceDetailComponent, InsuarenceUpdateComponent, InsuarenceDeleteDialogComponent],
  entryComponents: [InsuarenceDeleteDialogComponent]
})
export class RentautoInsuarenceModule {}
