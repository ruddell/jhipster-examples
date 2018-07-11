import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Photo } from 'app/shared/model/photo.model';
import { PhotoService } from './photo.service';
import { PhotoComponent } from './photo.component';
import { PhotoDetailComponent } from './photo-detail.component';
import { PhotoUpdateComponent } from './photo-update.component';
import { PhotoDeletePopupComponent } from './photo-delete-dialog.component';
import { IPhoto } from 'app/shared/model/photo.model';

@Injectable({ providedIn: 'root' })
export class PhotoResolve implements Resolve<IPhoto> {
    constructor(private service: PhotoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((photo: HttpResponse<Photo>) => photo.body);
        }
        return Observable.of(new Photo());
    }
}

export const photoRoute: Routes = [
    {
        path: 'photo',
        component: PhotoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jHipsterPress2App.photo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'photo/:id/view',
        component: PhotoDetailComponent,
        resolve: {
            photo: PhotoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.photo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'photo/new',
        component: PhotoUpdateComponent,
        resolve: {
            photo: PhotoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.photo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'photo/:id/edit',
        component: PhotoUpdateComponent,
        resolve: {
            photo: PhotoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.photo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const photoPopupRoute: Routes = [
    {
        path: 'photo/:id/delete',
        component: PhotoDeletePopupComponent,
        resolve: {
            photo: PhotoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.photo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
