import * as React from 'react';
import { Route, Switch } from 'react-router-dom';
import { ModalRoute } from 'react-router-modal';

import Foo from './foo';
import FooDetail from './foo-detail';
import FooDialog from './foo-dialog';
import FooDeleteDialog from './foo-delete-dialog';

const Routes = ({ match }) => (
  <div>
    <Switch>
      <Route exact path={match.url} component={Foo} />
      <ModalRoute exact parentPath={match.url} path={`${match.url}/new`} component={FooDialog} />
      <ModalRoute exact parentPath={match.url} path={`${match.url}/:id/delete`} component={FooDeleteDialog} />
      <ModalRoute exact parentPath={match.url} path={`${match.url}/:id/edit`} component={FooDialog} />
      <Route exact path={`${match.url}/:id`} component={FooDetail} />
    </Switch>
  </div>
);

export default Routes;
