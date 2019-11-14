import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FooComponentsPage, FooDeleteDialog, FooUpdatePage } from './foo.page-object';

const expect = chai.expect;

describe('Foo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fooComponentsPage: FooComponentsPage;
  let fooUpdatePage: FooUpdatePage;
  let fooDeleteDialog: FooDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Foos', async () => {
    await navBarPage.goToEntity('foo');
    fooComponentsPage = new FooComponentsPage();
    await browser.wait(ec.visibilityOf(fooComponentsPage.title), 5000);
    expect(await fooComponentsPage.getTitle()).to.eq('spingularApp.foo.home.title');
  });

  it('should load create Foo page', async () => {
    await fooComponentsPage.clickOnCreateButton();
    fooUpdatePage = new FooUpdatePage();
    expect(await fooUpdatePage.getPageTitle()).to.eq('spingularApp.foo.home.createOrEditLabel');
    await fooUpdatePage.cancel();
  });

  it('should create and save Foos', async () => {
    const nbButtonsBeforeCreate = await fooComponentsPage.countDeleteButtons();

    await fooComponentsPage.clickOnCreateButton();
    await promise.all([fooUpdatePage.setNameInput('name')]);
    expect(await fooUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    await fooUpdatePage.save();
    expect(await fooUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await fooComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Foo', async () => {
    const nbButtonsBeforeDelete = await fooComponentsPage.countDeleteButtons();
    await fooComponentsPage.clickOnLastDeleteButton();

    fooDeleteDialog = new FooDeleteDialog();
    expect(await fooDeleteDialog.getDialogTitle()).to.eq('spingularApp.foo.delete.question');
    await fooDeleteDialog.clickOnConfirmButton();

    expect(await fooComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
