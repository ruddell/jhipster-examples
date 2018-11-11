/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterTestModule } from '../../../test.module';
import { BlogPostDeleteDialogComponent } from 'app/entities/blog-post/blog-post-delete-dialog.component';
import { BlogPostService } from 'app/entities/blog-post/blog-post.service';

describe('Component Tests', () => {
    describe('BlogPost Management Delete Component', () => {
        let comp: BlogPostDeleteDialogComponent;
        let fixture: ComponentFixture<BlogPostDeleteDialogComponent>;
        let service: BlogPostService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [BlogPostDeleteDialogComponent]
            })
                .overrideTemplate(BlogPostDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BlogPostDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlogPostService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
