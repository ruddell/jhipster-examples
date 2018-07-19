/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeBankUpdateComponent } from 'app/entities/bridge-bank/bridge-bank-update.component';
import { BridgeBankService } from 'app/entities/bridge-bank/bridge-bank.service';
import { BridgeBank } from 'app/shared/model/bridge-bank.model';

describe('Component Tests', () => {
    describe('BridgeBank Management Update Component', () => {
        let comp: BridgeBankUpdateComponent;
        let fixture: ComponentFixture<BridgeBankUpdateComponent>;
        let service: BridgeBankService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeBankUpdateComponent]
            })
                .overrideTemplate(BridgeBankUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BridgeBankUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeBankService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BridgeBank(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bridgeBank = entity;
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
                    const entity = new BridgeBank();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bridgeBank = entity;
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
