import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { JhiLanguageService } from 'ng-jhipster';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { JhiLanguageHelper } from 'app/core/language/language.helper';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html'
})
export class SettingsComponent implements OnInit {
  account!: Account;
  success = false;
  languages: string[];
  settingsForm = this.fb.group({
    firstName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    lastName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    email: [undefined, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    langKey: [undefined]
  });

  constructor(
    private accountService: AccountService,
    private fb: FormBuilder,
    private languageService: JhiLanguageService,
    private languageHelper: JhiLanguageHelper
  ) {
    this.languages = this.languageHelper.getAll();
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      this.updateForm(account);
    });
  }

  save(): void {
    this.success = false;

    this.account.firstName = this.settingsForm.get('firstName')!.value;
    this.account.lastName = this.settingsForm.get('lastName')!.value;
    this.account.email = this.settingsForm.get('email')!.value;
    this.account.langKey = this.settingsForm.get('langKey')!.value;

    this.accountService.save(this.account).subscribe(() => {
      this.success = true;

      this.accountService.identity(true).subscribe(account => {
        this.updateForm(account);
      });

      this.languageService.getCurrent().then(current => {
        if (this.account.langKey !== current) {
          this.languageService.changeLanguage(this.account.langKey);
        }
      });
    });
  }

  private updateForm(account: Account): void {
    this.settingsForm.patchValue({
      firstName: account.firstName,
      lastName: account.lastName,
      email: account.email,
      langKey: account.langKey
    });

    this.account = account;
  }
}
