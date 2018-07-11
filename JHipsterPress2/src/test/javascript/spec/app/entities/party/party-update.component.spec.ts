/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JHipsterPress2TestModule } from '../../../test.module';
import { PartyUpdateComponent } from 'app/entities/party/party-update.component';
import { PartyService } from 'app/entities/party/party.service';
import { Party } from 'app/shared/model/party.model';

describe('Component Tests', () => {
    describe('Party Management Update Component', () => {
        let comp: PartyUpdateComponent;
        let fixture: ComponentFixture<PartyUpdateComponent>;
        let service: PartyService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JHipsterPress2TestModule],
                declarations: [PartyUpdateComponent]
            })
                .overrideTemplate(PartyUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PartyUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Party(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.party = entity;
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
                    const entity = new Party();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.party = entity;
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
