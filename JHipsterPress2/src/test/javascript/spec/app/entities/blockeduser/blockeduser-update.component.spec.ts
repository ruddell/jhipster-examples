/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JHipsterPress2TestModule } from '../../../test.module';
import { BlockeduserUpdateComponent } from 'app/entities/blockeduser/blockeduser-update.component';
import { BlockeduserService } from 'app/entities/blockeduser/blockeduser.service';
import { Blockeduser } from 'app/shared/model/blockeduser.model';

describe('Component Tests', () => {
    describe('Blockeduser Management Update Component', () => {
        let comp: BlockeduserUpdateComponent;
        let fixture: ComponentFixture<BlockeduserUpdateComponent>;
        let service: BlockeduserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JHipsterPress2TestModule],
                declarations: [BlockeduserUpdateComponent]
            })
                .overrideTemplate(BlockeduserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BlockeduserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlockeduserService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Blockeduser(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.blockeduser = entity;
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
                    const entity = new Blockeduser();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.blockeduser = entity;
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
