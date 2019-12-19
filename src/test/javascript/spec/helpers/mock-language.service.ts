import { SpyObject } from './spyobject';
import { JhiLanguageService } from 'ng-jhipster';
import Spy = jasmine.Spy;

export class MockLanguageService extends SpyObject {
  getCurrentLanguageSpy: Spy;

  constructor() {
    super(JhiLanguageService);

    this.getCurrentLanguageSpy = this.spy('getCurrentLanguage').andReturn('en');
  }
}
