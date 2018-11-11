import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBlogPost } from 'app/shared/model/blog-post.model';

@Component({
    selector: 'jhi-blog-post-detail',
    templateUrl: './blog-post-detail.component.html'
})
export class BlogPostDetailComponent implements OnInit {
    blogPost: IBlogPost;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ blogPost }) => {
            this.blogPost = blogPost;
        });
    }

    previousState() {
        window.history.back();
    }
}
