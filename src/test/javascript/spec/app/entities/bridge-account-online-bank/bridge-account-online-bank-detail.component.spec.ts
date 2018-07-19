/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeAccountOnlineBankDetailComponent } from 'app/entities/bridge-account-online-bank/bridge-account-online-bank-detail.component';
import { BridgeAccountOnlineBank } from 'app/shared/model/bridge-account-online-bank.model';

describe('Component Tests', () => {
    describe('BridgeAccountOnlineBank Management Detail Component', () => {
        let comp: BridgeAccountOnlineBankDetailComponent;
        let fixture: ComponentFixture<BridgeAccountOnlineBankDetailComponent>;
        const route = ({ data: of({ bridgeAccountOnlineBank: new BridgeAccountOnlineBank(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeAccountOnlineBankDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BridgeAccountOnlineBankDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BridgeAccountOnlineBankDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bridgeAccountOnlineBank).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
