import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInsuarence, Insuarence } from 'app/shared/model/insuarence.model';
import { InsuarenceService } from './insuarence.service';
import { InsuarenceComponent } from './insuarence.component';
import { InsuarenceDetailComponent } from './insuarence-detail.component';
import { InsuarenceUpdateComponent } from './insuarence-update.component';

@Injectable({ providedIn: 'root' })
export class InsuarenceResolve implements Resolve<IInsuarence> {
  constructor(private service: InsuarenceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInsuarence> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((insuarence: HttpResponse<Insuarence>) => {
          if (insuarence.body) {
            return of(insuarence.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Insuarence());
  }
}

export const insuarenceRoute: Routes = [
  {
    path: '',
    component: InsuarenceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.insuarence.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InsuarenceDetailComponent,
    resolve: {
      insuarence: InsuarenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.insuarence.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InsuarenceUpdateComponent,
    resolve: {
      insuarence: InsuarenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.insuarence.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InsuarenceUpdateComponent,
    resolve: {
      insuarence: InsuarenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.insuarence.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
