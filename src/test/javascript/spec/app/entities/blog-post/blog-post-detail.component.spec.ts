/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { BlogPostDetailComponent } from 'app/entities/blog-post/blog-post-detail.component';
import { BlogPost } from 'app/shared/model/blog-post.model';

describe('Component Tests', () => {
    describe('BlogPost Management Detail Component', () => {
        let comp: BlogPostDetailComponent;
        let fixture: ComponentFixture<BlogPostDetailComponent>;
        const route = ({ data: of({ blogPost: new BlogPost(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [BlogPostDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BlogPostDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BlogPostDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.blogPost).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
