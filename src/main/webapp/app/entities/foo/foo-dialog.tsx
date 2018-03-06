import * as React from 'react';
import { connect } from 'react-redux';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvFeedback } from 'availity-reactstrap-validation';
import { Translate, ICrudGetAction, ICrudPutAction } from 'react-jhipster';
import { FaBan, FaFloppyO } from 'react-icons/lib/fa';

import { getEntity, updateEntity, createEntity } from './foo.reducer';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';

export interface IFooDialogProps {
  getEntity: ICrudGetAction;
  updateEntity: ICrudPutAction;
  createEntity: ICrudPutAction;
  loading: boolean;
  updating: boolean;
  foo: any;
  match: any;
  history: any;
}

export interface IFooDialogState {
  showModal: boolean;
  isNew: boolean;
}

export class FooDialog extends React.Component<IFooDialogProps, IFooDialogState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id,
      showModal: true
    };
  }

  componentDidMount() {
    !this.state.isNew && this.props.getEntity(this.props.match.params.id);
  }

  saveEntity = (event, errors, values) => {
    if (this.state.isNew) {
      this.props.createEntity(values);
    } else {
      this.props.updateEntity(values);
    }
    this.handleClose();
  };

  handleClose = () => {
    this.setState({
      showModal: false
    });
    this.props.history.push('/foo');
  };

  render() {
    const isInvalid = false;
    const { foo, loading, updating } = this.props;
    const { showModal, isNew } = this.state;
    return (
      <Modal isOpen={showModal} modalTransition={{ timeout: 20 }} backdropTransition={{ timeout: 10 }} toggle={this.handleClose} size="lg">
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="jhipsterApp.foo.home.createOrEditLabel">Create or edit a Foo</Translate>
        </ModalHeader>
        {loading ? (
          <p>Loading...</p>
        ) : (
          <AvForm model={isNew ? {} : foo} onSubmit={this.saveEntity}>
            <ModalBody>
              {foo.id ? (
                <AvGroup>
                  <Label for="id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="name">
                  <Translate contentKey="jhipsterApp.foo.name">name</Translate>
                </Label>
                <AvInput type="text" className="form-control" name="name" required />
                <AvFeedback>This field is required.</AvFeedback>
                <AvFeedback>This field cannot be longer than 50 characters.</AvFeedback>
              </AvGroup>
            </ModalBody>
            <ModalFooter>
              <Button color="secondary" onClick={this.handleClose}>
                <FaBan />&nbsp;
                <Translate contentKey="entity.action.cancel">Cancel</Translate>
              </Button>
              <Button color="primary" type="submit" disabled={isInvalid || updating}>
                <FaFloppyO />&nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ModalFooter>
          </AvForm>
        )}
      </Modal>
    );
  }
}

const mapStateToProps = storeState => ({
  foo: storeState.foo.entity,
  loading: storeState.foo.loading,
  updating: storeState.foo.updating
});

const mapDispatchToProps = { getEntity, updateEntity, createEntity };

export default connect(mapStateToProps, mapDispatchToProps)(FooDialog);
