/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeAccountBankUpdateComponent } from 'app/entities/bridge-account-bank/bridge-account-bank-update.component';
import { BridgeAccountBankService } from 'app/entities/bridge-account-bank/bridge-account-bank.service';
import { BridgeAccountBank } from 'app/shared/model/bridge-account-bank.model';

describe('Component Tests', () => {
    describe('BridgeAccountBank Management Update Component', () => {
        let comp: BridgeAccountBankUpdateComponent;
        let fixture: ComponentFixture<BridgeAccountBankUpdateComponent>;
        let service: BridgeAccountBankService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeAccountBankUpdateComponent]
            })
                .overrideTemplate(BridgeAccountBankUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BridgeAccountBankUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeAccountBankService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BridgeAccountBank(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bridgeAccountBank = entity;
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
                    const entity = new BridgeAccountBank();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bridgeAccountBank = entity;
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
