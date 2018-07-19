/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FinaltestTestModule } from '../../../test.module';
import { AccountBankStatDeleteDialogComponent } from 'app/entities/account-bank-stat/account-bank-stat-delete-dialog.component';
import { AccountBankStatService } from 'app/entities/account-bank-stat/account-bank-stat.service';

describe('Component Tests', () => {
    describe('AccountBankStat Management Delete Component', () => {
        let comp: AccountBankStatDeleteDialogComponent;
        let fixture: ComponentFixture<AccountBankStatDeleteDialogComponent>;
        let service: AccountBankStatService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [AccountBankStatDeleteDialogComponent]
            })
                .overrideTemplate(AccountBankStatDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AccountBankStatDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccountBankStatService);
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
