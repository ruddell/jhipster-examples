import * as React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { Button } from 'reactstrap';
// TODO import TextFormat only when fieldContainsDate
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FaArrowLeft } from 'react-icons/lib/fa';

import { getEntity } from './foo.reducer';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFooDetailProps {
  getEntity: ICrudGetAction;
  foo: any;
  match: any;
}

export class FooDetail extends React.Component<IFooDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { foo } = this.props;
    return (
      <div>
        <h2>
          <Translate contentKey="jhipsterApp.foo.detail.title">Foo</Translate> [<b>{foo.id}</b>]
        </h2>
        <dl className="row-md jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterApp.foo.name">name</Translate>
            </span>
          </dt>
          <dd>{foo.name}</dd>
        </dl>
        <Button tag={Link} to="/foo" replace color="info">
          <FaArrowLeft />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
      </div>
    );
  }
}

const mapStateToProps = storeState => ({
  foo: storeState.foo.entity
});

const mapDispatchToProps = { getEntity };

export default connect(mapStateToProps, mapDispatchToProps)(FooDetail);
