import { TagMsgPage } from './app.po';

describe('tag-msg App', () => {
  let page: TagMsgPage;

  beforeEach(() => {
    page = new TagMsgPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
