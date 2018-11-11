import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BlogPost } from 'app/shared/model/blog-post.model';
import { BlogPostService } from './blog-post.service';
import { BlogPostComponent } from './blog-post.component';
import { BlogPostDetailComponent } from './blog-post-detail.component';
import { BlogPostUpdateComponent } from './blog-post-update.component';
import { BlogPostDeletePopupComponent } from './blog-post-delete-dialog.component';
import { IBlogPost } from 'app/shared/model/blog-post.model';

@Injectable({ providedIn: 'root' })
export class BlogPostResolve implements Resolve<IBlogPost> {
    constructor(private service: BlogPostService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<BlogPost> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BlogPost>) => response.ok),
                map((blogPost: HttpResponse<BlogPost>) => blogPost.body)
            );
        }
        return of(new BlogPost());
    }
}

export const blogPostRoute: Routes = [
    {
        path: 'blog-post',
        component: BlogPostComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterApp.blogPost.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'blog-post/:id/view',
        component: BlogPostDetailComponent,
        resolve: {
            blogPost: BlogPostResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.blogPost.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'blog-post/new',
        component: BlogPostUpdateComponent,
        resolve: {
            blogPost: BlogPostResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.blogPost.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'blog-post/:id/edit',
        component: BlogPostUpdateComponent,
        resolve: {
            blogPost: BlogPostResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.blogPost.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const blogPostPopupRoute: Routes = [
    {
        path: 'blog-post/:id/delete',
        component: BlogPostDeletePopupComponent,
        resolve: {
            blogPost: BlogPostResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.blogPost.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
