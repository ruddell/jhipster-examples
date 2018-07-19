/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FinaltestTestModule } from '../../../test.module';
import { AccountBankStatDetailComponent } from 'app/entities/account-bank-stat/account-bank-stat-detail.component';
import { AccountBankStat } from 'app/shared/model/account-bank-stat.model';

describe('Component Tests', () => {
    describe('AccountBankStat Management Detail Component', () => {
        let comp: AccountBankStatDetailComponent;
        let fixture: ComponentFixture<AccountBankStatDetailComponent>;
        const route = ({ data: of({ accountBankStat: new AccountBankStat(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [AccountBankStatDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AccountBankStatDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AccountBankStatDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.accountBankStat).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
