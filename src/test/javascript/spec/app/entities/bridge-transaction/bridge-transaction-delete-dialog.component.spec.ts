/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeTransactionDeleteDialogComponent } from 'app/entities/bridge-transaction/bridge-transaction-delete-dialog.component';
import { BridgeTransactionService } from 'app/entities/bridge-transaction/bridge-transaction.service';

describe('Component Tests', () => {
    describe('BridgeTransaction Management Delete Component', () => {
        let comp: BridgeTransactionDeleteDialogComponent;
        let fixture: ComponentFixture<BridgeTransactionDeleteDialogComponent>;
        let service: BridgeTransactionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeTransactionDeleteDialogComponent]
            })
                .overrideTemplate(BridgeTransactionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BridgeTransactionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeTransactionService);
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
