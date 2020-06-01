import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAutopark, Autopark } from 'app/shared/model/autopark.model';
import { AutoparkService } from './autopark.service';
import { AutoparkComponent } from './autopark.component';
import { AutoparkDetailComponent } from './autopark-detail.component';
import { AutoparkUpdateComponent } from './autopark-update.component';

@Injectable({ providedIn: 'root' })
export class AutoparkResolve implements Resolve<IAutopark> {
  constructor(private service: AutoparkService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAutopark> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((autopark: HttpResponse<Autopark>) => {
          if (autopark.body) {
            return of(autopark.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Autopark());
  }
}

export const autoparkRoute: Routes = [
  {
    path: '',
    component: AutoparkComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.autopark.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AutoparkDetailComponent,
    resolve: {
      autopark: AutoparkResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.autopark.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AutoparkUpdateComponent,
    resolve: {
      autopark: AutoparkResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.autopark.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AutoparkUpdateComponent,
    resolve: {
      autopark: AutoparkResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rentautoApp.autopark.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
