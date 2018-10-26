/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MonoTestModule } from '../../../test.module';
import { BarUpdateComponent } from 'app/entities/bar/bar-update.component';
import { BarService } from 'app/entities/bar/bar.service';
import { Bar } from 'app/shared/model/bar.model';

describe('Component Tests', () => {
    describe('Bar Management Update Component', () => {
        let comp: BarUpdateComponent;
        let fixture: ComponentFixture<BarUpdateComponent>;
        let service: BarService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MonoTestModule],
                declarations: [BarUpdateComponent]
            })
                .overrideTemplate(BarUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BarUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BarService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bar(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bar = entity;
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
                    const entity = new Bar();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bar = entity;
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
