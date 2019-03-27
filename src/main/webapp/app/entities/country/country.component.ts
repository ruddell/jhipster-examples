import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICountry } from 'app/shared/model/country.model';
import { AccountService } from 'app/core';
import { CountryService } from './country.service';

@Component({
  selector: 'jhi-country',
  templateUrl: './country.component.html'
})
export class CountryComponent implements OnInit, OnDestroy {
  countries: ICountry[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected countryService: CountryService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.countryService
      .query()
      .pipe(
        filter((res: HttpResponse<ICountry[]>) => res.ok),
        map((res: HttpResponse<ICountry[]>) => res.body)
      )
      .subscribe(
        (res: ICountry[]) => {
          this.countries = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCountries();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICountry) {
    return item.id;
  }

  registerChangeInCountries() {
    this.eventSubscriber = this.eventManager.subscribe('countryListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
