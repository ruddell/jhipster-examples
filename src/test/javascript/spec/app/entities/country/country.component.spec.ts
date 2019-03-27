/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MonoTestModule } from '../../../test.module';
import { CountryComponent } from 'app/entities/country/country.component';
import { CountryService } from 'app/entities/country/country.service';
import { Country } from 'app/shared/model/country.model';

describe('Component Tests', () => {
  describe('Country Management Component', () => {
    let comp: CountryComponent;
    let fixture: ComponentFixture<CountryComponent>;
    let service: CountryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonoTestModule],
        declarations: [CountryComponent],
        providers: []
      })
        .overrideTemplate(CountryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CountryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CountryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Country(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.countries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
