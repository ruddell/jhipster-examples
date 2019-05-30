import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPledge, defaultValue } from 'app/shared/model/pledge.model';

export const ACTION_TYPES = {
  FETCH_PLEDGE_LIST: 'pledge/FETCH_PLEDGE_LIST',
  FETCH_PLEDGE: 'pledge/FETCH_PLEDGE',
  CREATE_PLEDGE: 'pledge/CREATE_PLEDGE',
  UPDATE_PLEDGE: 'pledge/UPDATE_PLEDGE',
  DELETE_PLEDGE: 'pledge/DELETE_PLEDGE',
  RESET: 'pledge/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPledge>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type PledgeState = Readonly<typeof initialState>;

// Reducer

export default (state: PledgeState = initialState, action): PledgeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PLEDGE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PLEDGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PLEDGE):
    case REQUEST(ACTION_TYPES.UPDATE_PLEDGE):
    case REQUEST(ACTION_TYPES.DELETE_PLEDGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PLEDGE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PLEDGE):
    case FAILURE(ACTION_TYPES.CREATE_PLEDGE):
    case FAILURE(ACTION_TYPES.UPDATE_PLEDGE):
    case FAILURE(ACTION_TYPES.DELETE_PLEDGE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PLEDGE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PLEDGE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PLEDGE):
    case SUCCESS(ACTION_TYPES.UPDATE_PLEDGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PLEDGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/pledges';

// Actions

export const getEntities: ICrudGetAllAction<IPledge> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PLEDGE_LIST,
    payload: axios.get<IPledge>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IPledge> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PLEDGE,
    payload: axios.get<IPledge>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPledge> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PLEDGE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPledge> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PLEDGE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPledge> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PLEDGE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
