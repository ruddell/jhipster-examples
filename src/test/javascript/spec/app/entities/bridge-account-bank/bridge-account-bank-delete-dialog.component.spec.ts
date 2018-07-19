/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeAccountBankDeleteDialogComponent } from 'app/entities/bridge-account-bank/bridge-account-bank-delete-dialog.component';
import { BridgeAccountBankService } from 'app/entities/bridge-account-bank/bridge-account-bank.service';

describe('Component Tests', () => {
    describe('BridgeAccountBank Management Delete Component', () => {
        let comp: BridgeAccountBankDeleteDialogComponent;
        let fixture: ComponentFixture<BridgeAccountBankDeleteDialogComponent>;
        let service: BridgeAccountBankService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeAccountBankDeleteDialogComponent]
            })
                .overrideTemplate(BridgeAccountBankDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BridgeAccountBankDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeAccountBankService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
