/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeAccountOnlineBankDeleteDialogComponent } from 'app/entities/bridge-account-online-bank/bridge-account-online-bank-delete-dialog.component';
import { BridgeAccountOnlineBankService } from 'app/entities/bridge-account-online-bank/bridge-account-online-bank.service';

describe('Component Tests', () => {
    describe('BridgeAccountOnlineBank Management Delete Component', () => {
        let comp: BridgeAccountOnlineBankDeleteDialogComponent;
        let fixture: ComponentFixture<BridgeAccountOnlineBankDeleteDialogComponent>;
        let service: BridgeAccountOnlineBankService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeAccountOnlineBankDeleteDialogComponent]
            })
                .overrideTemplate(BridgeAccountOnlineBankDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BridgeAccountOnlineBankDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeAccountOnlineBankService);
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
