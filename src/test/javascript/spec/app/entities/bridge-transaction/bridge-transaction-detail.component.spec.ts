/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeTransactionDetailComponent } from 'app/entities/bridge-transaction/bridge-transaction-detail.component';
import { BridgeTransaction } from 'app/shared/model/bridge-transaction.model';

describe('Component Tests', () => {
    describe('BridgeTransaction Management Detail Component', () => {
        let comp: BridgeTransactionDetailComponent;
        let fixture: ComponentFixture<BridgeTransactionDetailComponent>;
        const route = ({ data: of({ bridgeTransaction: new BridgeTransaction(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeTransactionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BridgeTransactionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BridgeTransactionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bridgeTransaction).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
