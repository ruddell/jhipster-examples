/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JHipsterPress2TestModule } from '../../../test.module';
import { BlockeduserDetailComponent } from 'app/entities/blockeduser/blockeduser-detail.component';
import { Blockeduser } from 'app/shared/model/blockeduser.model';

describe('Component Tests', () => {
    describe('Blockeduser Management Detail Component', () => {
        let comp: BlockeduserDetailComponent;
        let fixture: ComponentFixture<BlockeduserDetailComponent>;
        const route = ({ data: of({ blockeduser: new Blockeduser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JHipsterPress2TestModule],
                declarations: [BlockeduserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BlockeduserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BlockeduserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.blockeduser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
