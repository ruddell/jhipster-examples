import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepartment } from 'app/shared/model/department.model';

@Component({
  selector: 'jhi-department-detail',
  templateUrl: './department-detail.component.html'
})
export class DepartmentDetailComponent implements OnInit {
  department: IDepartment;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ department }) => {
      this.department = department;
    });
  }

  previousState() {
    window.history.back();
  }
}
