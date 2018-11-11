import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBlogPost } from 'app/shared/model/blog-post.model';
import { BlogPostService } from './blog-post.service';

@Component({
    selector: 'jhi-blog-post-delete-dialog',
    templateUrl: './blog-post-delete-dialog.component.html'
})
export class BlogPostDeleteDialogComponent {
    blogPost: IBlogPost;

    constructor(private blogPostService: BlogPostService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.blogPostService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'blogPostListModification',
                content: 'Deleted an blogPost'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-blog-post-delete-popup',
    template: ''
})
export class BlogPostDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ blogPost }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BlogPostDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.blogPost = blogPost;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
