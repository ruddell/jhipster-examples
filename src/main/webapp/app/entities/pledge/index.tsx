import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Pledge from './pledge';
import PledgeDetail from './pledge-detail';
import PledgeUpdate from './pledge-update';
import PledgeDeleteDialog from './pledge-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PledgeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PledgeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PledgeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Pledge} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PledgeDeleteDialog} />
  </>
);

export default Routes;
