/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinaltestTestModule } from '../../../test.module';
import { AccountBankStatComponent } from 'app/entities/account-bank-stat/account-bank-stat.component';
import { AccountBankStatService } from 'app/entities/account-bank-stat/account-bank-stat.service';
import { AccountBankStat } from 'app/shared/model/account-bank-stat.model';

describe('Component Tests', () => {
    describe('AccountBankStat Management Component', () => {
        let comp: AccountBankStatComponent;
        let fixture: ComponentFixture<AccountBankStatComponent>;
        let service: AccountBankStatService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [AccountBankStatComponent],
                providers: []
            })
                .overrideTemplate(AccountBankStatComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AccountBankStatComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccountBankStatService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AccountBankStat(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.accountBankStats[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
