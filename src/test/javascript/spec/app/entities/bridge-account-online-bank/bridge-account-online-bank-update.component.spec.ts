/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeAccountOnlineBankUpdateComponent } from 'app/entities/bridge-account-online-bank/bridge-account-online-bank-update.component';
import { BridgeAccountOnlineBankService } from 'app/entities/bridge-account-online-bank/bridge-account-online-bank.service';
import { BridgeAccountOnlineBank } from 'app/shared/model/bridge-account-online-bank.model';

describe('Component Tests', () => {
    describe('BridgeAccountOnlineBank Management Update Component', () => {
        let comp: BridgeAccountOnlineBankUpdateComponent;
        let fixture: ComponentFixture<BridgeAccountOnlineBankUpdateComponent>;
        let service: BridgeAccountOnlineBankService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeAccountOnlineBankUpdateComponent]
            })
                .overrideTemplate(BridgeAccountOnlineBankUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BridgeAccountOnlineBankUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeAccountOnlineBankService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BridgeAccountOnlineBank(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bridgeAccountOnlineBank = entity;
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
                    const entity = new BridgeAccountOnlineBank();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bridgeAccountOnlineBank = entity;
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
