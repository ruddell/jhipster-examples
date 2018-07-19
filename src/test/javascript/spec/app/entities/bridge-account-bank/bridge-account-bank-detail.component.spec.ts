/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeAccountBankDetailComponent } from 'app/entities/bridge-account-bank/bridge-account-bank-detail.component';
import { BridgeAccountBank } from 'app/shared/model/bridge-account-bank.model';

describe('Component Tests', () => {
    describe('BridgeAccountBank Management Detail Component', () => {
        let comp: BridgeAccountBankDetailComponent;
        let fixture: ComponentFixture<BridgeAccountBankDetailComponent>;
        const route = ({ data: of({ bridgeAccountBank: new BridgeAccountBank(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeAccountBankDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BridgeAccountBankDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BridgeAccountBankDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bridgeAccountBank).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
