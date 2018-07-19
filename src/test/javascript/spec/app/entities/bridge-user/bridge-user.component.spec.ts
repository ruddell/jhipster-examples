/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeUserComponent } from 'app/entities/bridge-user/bridge-user.component';
import { BridgeUserService } from 'app/entities/bridge-user/bridge-user.service';
import { BridgeUser } from 'app/shared/model/bridge-user.model';

describe('Component Tests', () => {
    describe('BridgeUser Management Component', () => {
        let comp: BridgeUserComponent;
        let fixture: ComponentFixture<BridgeUserComponent>;
        let service: BridgeUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeUserComponent],
                providers: []
            })
                .overrideTemplate(BridgeUserComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BridgeUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeUserService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BridgeUser(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bridgeUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
