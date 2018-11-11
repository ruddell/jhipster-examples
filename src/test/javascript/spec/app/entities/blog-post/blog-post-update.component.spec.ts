/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { BlogPostUpdateComponent } from 'app/entities/blog-post/blog-post-update.component';
import { BlogPostService } from 'app/entities/blog-post/blog-post.service';
import { BlogPost } from 'app/shared/model/blog-post.model';

describe('Component Tests', () => {
    describe('BlogPost Management Update Component', () => {
        let comp: BlogPostUpdateComponent;
        let fixture: ComponentFixture<BlogPostUpdateComponent>;
        let service: BlogPostService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [BlogPostUpdateComponent]
            })
                .overrideTemplate(BlogPostUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BlogPostUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlogPostService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new BlogPost(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.blogPost = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new BlogPost();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.blogPost = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
