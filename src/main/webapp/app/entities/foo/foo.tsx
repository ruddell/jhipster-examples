import * as React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { Button } from 'reactstrap';
// TODO import TextFormat only when fieldContainsDate
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FaPlus, FaEye, FaPencil, FaTrash } from 'react-icons/lib/fa';

import { getEntities } from './foo.reducer';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFooProps {
  getEntities: ICrudGetAllAction;
  foos: any[];
  match: any;
}

export class Foo extends React.Component<IFooProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { foos, match } = this.props;
    return (
      <div>
        <h2>
          <Translate contentKey="jhipsterApp.foo.home.title">Foos</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity">
            <FaPlus /> <Translate contentKey="jhipsterApp.foo.home.createLabel" />
          </Link>
        </h2>
        <div className="table-responsive">
          <table className="table table-striped">
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.foo.name">Name</Translate> <span className="fa fa-sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {foos.map((foo, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${foo.id}`} color="link" size="sm">
                      {foo.id}
                    </Button>
                  </td>
                  <td>{foo.name}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${foo.id}`} color="info" size="sm">
                        <FaEye />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view" />
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${foo.id}/edit`} color="primary" size="sm">
                        <FaPencil />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit" />
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${foo.id}/delete`} color="danger" size="sm">
                        <FaTrash />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete" />
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = storeState => ({
  foos: storeState.foo.entities
});

const mapDispatchToProps = { getEntities };

export default connect(mapStateToProps, mapDispatchToProps)(Foo);
