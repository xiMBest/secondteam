import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RentautoSharedModule } from 'app/shared/shared.module';
import { FixcarComponent } from './fixcar.component';
import { FixcarDetailComponent } from './fixcar-detail.component';
import { FixcarUpdateComponent } from './fixcar-update.component';
import { FixcarDeleteDialogComponent } from './fixcar-delete-dialog.component';
import { fixcarRoute } from './fixcar.route';

@NgModule({
  imports: [RentautoSharedModule, RouterModule.forChild(fixcarRoute)],
  declarations: [FixcarComponent, FixcarDetailComponent, FixcarUpdateComponent, FixcarDeleteDialogComponent],
  entryComponents: [FixcarDeleteDialogComponent]
})
export class RentautoFixcarModule {}
