/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeUserUpdateComponent } from 'app/entities/bridge-user/bridge-user-update.component';
import { BridgeUserService } from 'app/entities/bridge-user/bridge-user.service';
import { BridgeUser } from 'app/shared/model/bridge-user.model';

describe('Component Tests', () => {
    describe('BridgeUser Management Update Component', () => {
        let comp: BridgeUserUpdateComponent;
        let fixture: ComponentFixture<BridgeUserUpdateComponent>;
        let service: BridgeUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeUserUpdateComponent]
            })
                .overrideTemplate(BridgeUserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BridgeUserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeUserService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BridgeUser(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bridgeUser = entity;
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
                    const entity = new BridgeUser();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bridgeUser = entity;
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
