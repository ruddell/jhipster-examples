/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MonoTestModule } from '../../../test.module';
import { FooUpdateComponent } from 'app/entities/foo/foo-update.component';
import { FooService } from 'app/entities/foo/foo.service';
import { Foo } from 'app/shared/model/foo.model';

describe('Component Tests', () => {
    describe('Foo Management Update Component', () => {
        let comp: FooUpdateComponent;
        let fixture: ComponentFixture<FooUpdateComponent>;
        let service: FooService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MonoTestModule],
                declarations: [FooUpdateComponent]
            })
                .overrideTemplate(FooUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FooUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FooService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Foo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.foo = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Foo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.foo = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
