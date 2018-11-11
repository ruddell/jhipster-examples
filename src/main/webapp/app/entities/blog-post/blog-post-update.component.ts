import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBlogPost } from 'app/shared/model/blog-post.model';
import { BlogPostService } from './blog-post.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-blog-post-update',
    templateUrl: './blog-post-update.component.html'
})
export class BlogPostUpdateComponent implements OnInit {
    blogPost: IBlogPost;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private blogPostService: BlogPostService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ blogPost }) => {
            this.blogPost = blogPost;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.blogPost.id !== undefined) {
            this.subscribeToSaveResponse(this.blogPostService.update(this.blogPost));
        } else {
            this.subscribeToSaveResponse(this.blogPostService.create(this.blogPost));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBlogPost>>) {
        result.subscribe((res: HttpResponse<IBlogPost>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
