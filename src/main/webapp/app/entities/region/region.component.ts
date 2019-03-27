import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRegion } from 'app/shared/model/region.model';
import { AccountService } from 'app/core';
import { RegionService } from './region.service';

@Component({
  selector: 'jhi-region',
  templateUrl: './region.component.html'
})
export class RegionComponent implements OnInit, OnDestroy {
  regions: IRegion[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected regionService: RegionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.regionService
      .query()
      .pipe(
        filter((res: HttpResponse<IRegion[]>) => res.ok),
        map((res: HttpResponse<IRegion[]>) => res.body)
      )
      .subscribe(
        (res: IRegion[]) => {
          this.regions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRegions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRegion) {
    return item.id;
  }

  registerChangeInRegions() {
    this.eventSubscriber = this.eventManager.subscribe('regionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
