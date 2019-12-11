import { SpyObject } from './spyobject';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import Spy = jasmine.Spy;

export class MockLanguageService extends SpyObject {
  getCurrentLanguageSpy: Spy;
  fakeResponse: string;

  constructor() {
    super(JhiLanguageService);

    this.fakeResponse = 'en';
    this.getCurrentLanguageSpy = this.spy('getCurrentLanguage').andReturn(this.fakeResponse);
  }
}

export class MockLanguageHelper extends SpyObject {
  getAllSpy: Spy;

  constructor() {
    super(JhiLanguageHelper);

    this.getAllSpy = this.spy('getAll').andReturn(Promise.resolve(['en', 'fr']));
  }
}
