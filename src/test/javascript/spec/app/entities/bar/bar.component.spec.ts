/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MonoTestModule } from '../../../test.module';
import { BarComponent } from 'app/entities/bar/bar.component';
import { BarService } from 'app/entities/bar/bar.service';
import { Bar } from 'app/shared/model/bar.model';

describe('Component Tests', () => {
    describe('Bar Management Component', () => {
        let comp: BarComponent;
        let fixture: ComponentFixture<BarComponent>;
        let service: BarService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MonoTestModule],
                declarations: [BarComponent],
                providers: []
            })
                .overrideTemplate(BarComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BarComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BarService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Bar(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bars[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
