import axios from 'axios';

import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

export const ACTION_TYPES = {
  FETCH_LOGS: 'administration/FETCH_LOGS',
  FETCH_LOGS_CHANGE_LEVEL: 'administration/FETCH_LOGS_CHANGE_LEVEL',
  FETCH_HEALTH: 'administration/FETCH_HEALTH',
  FETCH_METRICS: 'administration/FETCH_METRICS',
  FETCH_THREAD_DUMP: 'administration/FETCH_THREAD_DUMP',
  FETCH_CONFIGURATIONS: 'administration/FETCH_CONFIGURATIONS',
  FETCH_ENV: 'administration/FETCH_ENV',
  WEBSOCKET_ACTIVITY_MESSAGE: 'administration/WEBSOCKET_ACTIVITY_MESSAGE',
};

const initialState = {
  loading: false,
  errorMessage: null,
  logs: {
    loggers: [] as any[],
  },
  health: {} as any,
  metrics: {} as any,
  threadDump: [],
  configuration: {
    configProps: {} as any,
    env: {} as any,
  },
  tracker: {
    activities: [],
  },
  totalItems: 0,
};

export type AdministrationState = Readonly<typeof initialState>;

// Reducer

export default (state: AdministrationState = initialState, action): AdministrationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_METRICS):
    case REQUEST(ACTION_TYPES.FETCH_THREAD_DUMP):
    case REQUEST(ACTION_TYPES.FETCH_LOGS):
    case REQUEST(ACTION_TYPES.FETCH_CONFIGURATIONS):
    case REQUEST(ACTION_TYPES.FETCH_ENV):
    case REQUEST(ACTION_TYPES.FETCH_HEALTH):
      return {
        ...state,
        errorMessage: null,
        loading: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_METRICS):
    case FAILURE(ACTION_TYPES.FETCH_THREAD_DUMP):
    case FAILURE(ACTION_TYPES.FETCH_LOGS):
    case FAILURE(ACTION_TYPES.FETCH_CONFIGURATIONS):
    case FAILURE(ACTION_TYPES.FETCH_ENV):
    case FAILURE(ACTION_TYPES.FETCH_HEALTH):
      return {
        ...state,
        loading: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_METRICS):
      return {
        ...state,
        loading: false,
        metrics: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_THREAD_DUMP):
      return {
        ...state,
        loading: false,
        threadDump: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LOGS):
      return {
        ...state,
        loading: false,
        logs: {
          loggers: action.payload.data.loggers,
        },
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONFIGURATIONS):
      return {
        ...state,
        loading: false,
        configuration: {
          ...state.configuration,
          configProps: action.payload.data,
        },
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENV):
      return {
        ...state,
        loading: false,
        configuration: {
          ...state.configuration,
          env: action.payload.data,
        },
      };
    case SUCCESS(ACTION_TYPES.FETCH_HEALTH):
      return {
        ...state,
        loading: false,
        health: action.payload.data,
      };
    case ACTION_TYPES.WEBSOCKET_ACTIVITY_MESSAGE: {
      // filter out activities from the same session
      const uniqueActivities = state.tracker.activities.filter(activity => activity.sessionId !== action.payload.sessionId);
      // remove any activities with the page of logout
      const activities = [...uniqueActivities, action.payload].filter(activity => activity.page !== 'logout');
      return {
        ...state,
        tracker: { activities },
      };
    }
    default:
      return state;
  }
};

// Actions

export const systemHealth = () => ({
  type: ACTION_TYPES.FETCH_HEALTH,
  payload: axios.get('management/health'),
});

export const systemMetrics = () => ({
  type: ACTION_TYPES.FETCH_METRICS,
  payload: axios.get('management/jhimetrics'),
});

export const systemThreadDump = () => ({
  type: ACTION_TYPES.FETCH_THREAD_DUMP,
  payload: axios.get('management/threaddump'),
});

export const getLoggers = () => ({
  type: ACTION_TYPES.FETCH_LOGS,
  payload: axios.get('management/loggers'),
});

export const changeLogLevel: (name, configuredLevel) => void = (name, configuredLevel) => {
  const body = { configuredLevel };
  return async dispatch => {
    await dispatch({
      type: ACTION_TYPES.FETCH_LOGS_CHANGE_LEVEL,
      payload: axios.post('management/loggers/' + name, body),
    });
    dispatch(getLoggers());
  };
};

export const getConfigurations = () => ({
  type: ACTION_TYPES.FETCH_CONFIGURATIONS,
  payload: axios.get('management/configprops'),
});

export const getEnv = () => ({
  type: ACTION_TYPES.FETCH_ENV,
  payload: axios.get('management/env'),
});
