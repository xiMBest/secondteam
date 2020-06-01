import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReservecar, Reservecar } from 'app/shared/model/reservecar.model';
import { ReservecarService } from './reservecar.service';
import { ReservecarComponent } from './reservecar.component';
import { ReservecarDetailComponent } from './reservecar-detail.component';
import { ReservecarUpdateComponent } from './reservecar-update.component';

@Injectable({ providedIn: 'root' })
export class ReservecarResolve implements Resolve<IReservecar> {
  constructor(private service: ReservecarService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReservecar> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((reservecar: HttpResponse<Reservecar>) => {
          if (reservecar.body) {
            return of(reservecar.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Reservecar());
  }
}

export const reservecarRoute: Routes = [
  {
    path: '',
    component: ReservecarComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.reservecar.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReservecarDetailComponent,
    resolve: {
      reservecar: ReservecarResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.reservecar.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReservecarUpdateComponent,
    resolve: {
      reservecar: ReservecarResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.reservecar.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReservecarUpdateComponent,
    resolve: {
      reservecar: ReservecarResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.reservecar.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
