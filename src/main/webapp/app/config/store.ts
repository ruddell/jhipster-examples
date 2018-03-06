import { createStore, applyMiddleware, compose } from 'redux';
import promiseMiddleware from 'redux-promise-middleware';
import thunkMiddleware from 'redux-thunk';
import reducer from 'app/shared/reducers';
import DevTools from './devtools';
import errorMiddleware from './error-middleware';
import notificationMiddleware from './notification-middleware';
import loggerMiddleware from './logger-middleware';
import websocketMiddleware from './websocket-middleware';
import Reactotron from 'reactotron-react-js';
import { loadingBarMiddleware } from 'react-redux-loading-bar';
import { routerMiddleware } from 'react-router-redux';

import { history } from '../app';

const defaultMiddlewares = [
  thunkMiddleware,
  errorMiddleware,
  notificationMiddleware,
  promiseMiddleware(),
  loadingBarMiddleware(),
  websocketMiddleware,
  routerMiddleware(history),
  loggerMiddleware
];
const composedMiddlewares = middlewares =>
  process.env.NODE_ENV === 'development'
    ? compose(applyMiddleware(...defaultMiddlewares, ...middlewares), DevTools.instrument())
    : compose(applyMiddleware(...defaultMiddlewares, ...middlewares));

const initialize = (initialState = {}, middlewares = []) =>
  process.env.NODE_ENV === 'development'
    ? Reactotron.createStore(reducer, initialState, composedMiddlewares(middlewares))
    : createStore(reducer, initialState, composedMiddlewares(middlewares));

export default initialize;
