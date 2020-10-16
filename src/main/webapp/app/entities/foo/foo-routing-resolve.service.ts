import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { IFoo, Foo } from 'app/shared/model/foo.model';
import { FooService } from './foo.service';

@Injectable({ providedIn: 'root' })
export class FooRoutingResolveService implements Resolve<IFoo> {
  constructor(private service: FooService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFoo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((foo: HttpResponse<Foo>) => {
          if (foo.body) {
            return of(foo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Foo());
  }
}
