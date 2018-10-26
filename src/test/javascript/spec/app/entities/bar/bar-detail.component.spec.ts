/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MonoTestModule } from '../../../test.module';
import { BarDetailComponent } from 'app/entities/bar/bar-detail.component';
import { Bar } from 'app/shared/model/bar.model';

describe('Component Tests', () => {
    describe('Bar Management Detail Component', () => {
        let comp: BarDetailComponent;
        let fixture: ComponentFixture<BarDetailComponent>;
        const route = ({ data: of({ bar: new Bar(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MonoTestModule],
                declarations: [BarDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BarDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BarDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bar).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
