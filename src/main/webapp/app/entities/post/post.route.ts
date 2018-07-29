import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Post } from 'app/shared/model/post.model';
import { PostService } from './post.service';
import { PostComponent } from './post.component';
import { PostDetailComponent } from './post-detail.component';
import { PostUpdateComponent } from './post-update.component';
import { PostDeletePopupComponent } from './post-delete-dialog.component';
import { IPost } from 'app/shared/model/post.model';

@Injectable({ providedIn: 'root' })
export class PostResolve implements Resolve<IPost> {
    constructor(private service: PostService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((post: HttpResponse<Post>) => post.body);
        }
        return Observable.of(new Post());
    }
}

export const postRoute: Routes = [
    {
        path: 'post',
        component: PostComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Posts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'post/:id/view',
        component: PostDetailComponent,
        resolve: {
            post: PostResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Posts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'post/new',
        component: PostUpdateComponent,
        resolve: {
            post: PostResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Posts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'post/:id/edit',
        component: PostUpdateComponent,
        resolve: {
            post: PostResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Posts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const postPopupRoute: Routes = [
    {
        path: 'post/:id/delete',
        component: PostDeletePopupComponent,
        resolve: {
            post: PostResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Posts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
