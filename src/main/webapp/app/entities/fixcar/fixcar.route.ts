import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFixcar, Fixcar } from 'app/shared/model/fixcar.model';
import { FixcarService } from './fixcar.service';
import { FixcarComponent } from './fixcar.component';
import { FixcarDetailComponent } from './fixcar-detail.component';
import { FixcarUpdateComponent } from './fixcar-update.component';

@Injectable({ providedIn: 'root' })
export class FixcarResolve implements Resolve<IFixcar> {
  constructor(private service: FixcarService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFixcar> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fixcar: HttpResponse<Fixcar>) => {
          if (fixcar.body) {
            return of(fixcar.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Fixcar());
  }
}

export const fixcarRoute: Routes = [
  {
    path: '',
    component: FixcarComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.fixcar.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FixcarDetailComponent,
    resolve: {
      fixcar: FixcarResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.fixcar.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FixcarUpdateComponent,
    resolve: {
      fixcar: FixcarResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.fixcar.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FixcarUpdateComponent,
    resolve: {
      fixcar: FixcarResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.fixcar.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
