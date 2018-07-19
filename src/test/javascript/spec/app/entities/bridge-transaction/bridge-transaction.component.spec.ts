/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeTransactionComponent } from 'app/entities/bridge-transaction/bridge-transaction.component';
import { BridgeTransactionService } from 'app/entities/bridge-transaction/bridge-transaction.service';
import { BridgeTransaction } from 'app/shared/model/bridge-transaction.model';

describe('Component Tests', () => {
    describe('BridgeTransaction Management Component', () => {
        let comp: BridgeTransactionComponent;
        let fixture: ComponentFixture<BridgeTransactionComponent>;
        let service: BridgeTransactionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeTransactionComponent],
                providers: []
            })
                .overrideTemplate(BridgeTransactionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BridgeTransactionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeTransactionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BridgeTransaction(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bridgeTransactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
