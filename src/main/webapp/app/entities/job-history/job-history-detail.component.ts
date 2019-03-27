import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobHistory } from 'app/shared/model/job-history.model';

@Component({
  selector: 'jhi-job-history-detail',
  templateUrl: './job-history-detail.component.html'
})
export class JobHistoryDetailComponent implements OnInit {
  jobHistory: IJobHistory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ jobHistory }) => {
      this.jobHistory = jobHistory;
    });
  }

  previousState() {
    window.history.back();
  }
}
