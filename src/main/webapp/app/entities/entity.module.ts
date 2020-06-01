import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'autopark',
        loadChildren: () => import('./autopark/autopark.module').then(m => m.RentautoAutoparkModule)
      },
      {
        path: 'fixcar',
        loadChildren: () => import('./fixcar/fixcar.module').then(m => m.RentautoFixcarModule)
      },
      {
        path: 'rate',
        loadChildren: () => import('./rate/rate.module').then(m => m.RentautoRateModule)
      },
      {
        path: 'insuarence',
        loadChildren: () => import('./insuarence/insuarence.module').then(m => m.RentautoInsuarenceModule)
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.RentautoCustomerModule)
      },
      {
        path: 'penalty',
        loadChildren: () => import('./penalty/penalty.module').then(m => m.RentautoPenaltyModule)
      },
      {
        path: 'reservecar',
        loadChildren: () => import('./reservecar/reservecar.module').then(m => m.RentautoReservecarModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class RentautoEntityModule {}
