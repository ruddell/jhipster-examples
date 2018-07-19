/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeTransactionUpdateComponent } from 'app/entities/bridge-transaction/bridge-transaction-update.component';
import { BridgeTransactionService } from 'app/entities/bridge-transaction/bridge-transaction.service';
import { BridgeTransaction } from 'app/shared/model/bridge-transaction.model';

describe('Component Tests', () => {
    describe('BridgeTransaction Management Update Component', () => {
        let comp: BridgeTransactionUpdateComponent;
        let fixture: ComponentFixture<BridgeTransactionUpdateComponent>;
        let service: BridgeTransactionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeTransactionUpdateComponent]
            })
                .overrideTemplate(BridgeTransactionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BridgeTransactionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeTransactionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BridgeTransaction(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bridgeTransaction = entity;
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
                    const entity = new BridgeTransaction();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bridgeTransaction = entity;
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
