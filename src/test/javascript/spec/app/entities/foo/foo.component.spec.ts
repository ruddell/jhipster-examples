/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MonoTestModule } from '../../../test.module';
import { FooComponent } from 'app/entities/foo/foo.component';
import { FooService } from 'app/entities/foo/foo.service';
import { Foo } from 'app/shared/model/foo.model';

describe('Component Tests', () => {
    describe('Foo Management Component', () => {
        let comp: FooComponent;
        let fixture: ComponentFixture<FooComponent>;
        let service: FooService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MonoTestModule],
                declarations: [FooComponent],
                providers: []
            })
                .overrideTemplate(FooComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FooComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FooService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Foo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.foos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
